package com.dragon.shadowsocks.service;

import com.dragon.shadowsocks.repository.PlistRepository;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cjw on 2017/7/8.
 */
@Component
public class ScheduledFactory {

    @Autowired
    private ShadowSocksRepository shadowSocksRepository;

    @Autowired
    private PlistRepository plistRepository;

    public ScheduledExecutor createScheduledExecutor() {
        return new ScheduledExecutor(shadowSocksRepository, plistRepository);
    }
}
