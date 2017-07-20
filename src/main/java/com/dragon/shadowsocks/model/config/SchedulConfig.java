package com.dragon.shadowsocks.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by cjw on 2017/7/8.
 * 调度配置
 */
@Component
@ConfigurationProperties(prefix = "schedul")
public class SchedulConfig {
    private int initialDelay;
    private int period;
    private String profileHost;

    public int getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getProfileHost() {
        return profileHost;
    }

    public void setProfileHost(String profileHost) {
        this.profileHost = profileHost;
    }
}
