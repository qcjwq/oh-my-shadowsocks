package com.dragon.shadowsocks.service;

import com.dragon.shadowsocks.model.config.SchedulConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class FuckGFW implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(FuckGFW.class);

    @Autowired
    private SchedulConfig schedulConfig;

    @Autowired
    private ScheduledFactory scheduledFactory;

    @Override
    public void run(String... strings) throws Exception {
        if (true) {
            //return;
        }

        ScheduledExecutorService schedul = Executors.newScheduledThreadPool(10);

        long initialDelay = schedulConfig.getInitialDelay();
        long period = schedulConfig.getPeriod();
        ScheduledExecutor command = scheduledFactory.createScheduledExecutor();

        String message = MessageFormat.format("调度计划：initialDelay={0},period={1}", initialDelay, period);
        logger.debug(message);
        logger.info("任务调度开始...");
        schedul.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.SECONDS);
    }
}
