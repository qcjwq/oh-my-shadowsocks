package com.dragon.shadowsocks.common.aop;

import com.alibaba.fastjson.JSON;
import com.dragon.shadowsocks.model.VisitLogModel;
import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cjw on 2017/6/18.
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Value("${visitlog.enabled}")
    private String visitLog;

    @Pointcut("execution(public * com.dragon.shadowsocks..*.*(..))")
    public void serverPointcut() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    protected void getMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    protected void postMapping() {
    }

    @Pointcut("serverPointcut() && (getMapping() || postMapping())")
    public void pointcut() {
    }

    /**
     * 环绕切入包下所有RequestMapping注解方法
     *
     * @param jp the jp
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("pointcut()")
    public Object processTx(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        VisitLogModel logInfo = initLogger(jp, start);

        Object result = null;
        try {
            result = jp.proceed(jp.getArgs());

            logInfo.setReturnValue(result); //记录返回值
        } catch (Exception e) {
            logInfo.setExceptionTrace(e.getMessage());
            logger.error(e.getMessage(), e);
        } finally {
            long interval = System.currentTimeMillis() - start;
            setInterval(result, (int) interval);

            logInfo.setInterval(interval);
            logInfo.setTraceLogId(UUID.randomUUID().toString());

            if (visitLogEnabled()) {
                logger.info(JSON.toJSONString(logInfo));
            }
        }

        return result;
    }

    private VisitLogModel initLogger(ProceedingJoinPoint jp, long start) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        VisitLogModel logInfo = new VisitLogModel();
        logInfo.setClassName(jp.getSignature().getDeclaringTypeName());
        logInfo.setSignature(jp.getSignature().getName());
        logInfo.setUrl(request.getRequestURL().toString());
        logInfo.setHttpMethod(request.getMethod());
        logInfo.setIp(request.getRemoteAddr());
        logInfo.setRequestArgs(JSON.toJSONString(jp.getArgs())); //记录请求值
        logInfo.setTimestamp(new Date(start));
        return logInfo;
    }

    /**
     * 是否启用VisitLog
     *
     * @return the boolean
     */
    private boolean visitLogEnabled() {
        return BooleanUtils.toBoolean(visitLog);
    }

    /**
     * 通过反射设置Interval
     *
     * @param result   the result
     * @param interval the interval
     */
    private void setInterval(Object result, int interval) {
        try {
            Field headField = result.getClass().getDeclaredField("head");
            if (headField == null) {
                return;
            }

            headField.setAccessible(true);
            Field intervalField = headField.getType().getDeclaredField("interval");
            if (intervalField == null) {
                return;
            }

            intervalField.setAccessible(true);
            Object headClazz = headField.get(result);
            intervalField.set(headClazz, interval);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
