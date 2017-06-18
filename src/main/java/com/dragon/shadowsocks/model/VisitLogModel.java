package com.dragon.shadowsocks.model;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cjw on 2017/6/18.
 */

public class VisitLogModel implements Serializable {

    private String id;

    /**
     * 时间戳
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date timestamp;

    /**
     * 埋点id
     */
    private String traceLogId;

    /**
     * 耗时（毫秒）
     */
    private long interval;

    /**
     * 耗时区间
     */
    private String intervalScope;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法签名
     */
    private String signature;

    /**
     * request url
     */
    private String url;

    /**
     * http method
     */
    private String httpMethod;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object requestArgs;

    /**
     * 返回值
     */
    private Object returnValue;

    /**
     * 返回码
     */
    private int resultCode;

    /**
     * 返回内容
     */
    private String resultMessage;

    /**
     * 异常堆栈
     */
    private String exceptionTrace;

    public String getIntervalScope() {
        if (interval <= 10) {
            return "[0-10ms]";
        } else if (interval <= 25) {
            return "(10-25ms]";
        } else if (interval <= 50) {
            return "(25-50ms]";
        } else if (interval <= 100) {
            return "(50-100ms]";
        } else if (interval <= 250) {
            return "(100-250ms]";
        } else if (interval <= 500) {
            return "(250-500ms]";
        } else if (interval <= 1000) {
            return "(500-1000ms]";
        } else if (interval <= 3000) {
            return "(1-3s]";
        } else if (interval <= 5000) {
            return "(3-5s]";
        } else if (interval <= 10000) {
            return "(5-10s]";
        } else if (interval <= 15000) {
            return "(10-15s]";
        } else {
            return "(15s-∞]";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTraceLogId() {
        return traceLogId;
    }

    public void setTraceLogId(String traceLogId) {
        this.traceLogId = traceLogId;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Object getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(Object requestArgs) {
        this.requestArgs = requestArgs;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public void setExceptionTrace(String exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}