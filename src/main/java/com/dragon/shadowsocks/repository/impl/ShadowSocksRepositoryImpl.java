package com.dragon.shadowsocks.repository.impl;

import com.alibaba.fastjson.JSON;
import com.dragon.shadowsocks.common.utils.CommonUtil;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import com.dragon.shadowsocks.repository.UnirestRepository;
import com.dragon.shadowsocks.repository.UnixScriptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class ShadowSocksRepositoryImpl implements ShadowSocksRepository {
    private static final Logger logger = LoggerFactory.getLogger(ShadowSocksRepositoryImpl.class);

    @Autowired
    private UnirestRepository unirestRepository;

    @Autowired
    private UnixScriptRepository unixScriptRepository;

    @Override
    public ProfileList.Response getProfileList() {
        String result = unirestRepository.getString(CommonUtil.getProfileListUrl());
        return JSON.parseObject(result, ProfileList.Response.class);
    }

    @Override
    public void importConfig() {
        logger.debug("import ShadowsocksX plist");
        unixScriptRepository.execute("defaults import clowwindy.ShadowsocksX $HOME/Library/Preferences/clowwindy.ShadowsocksX.plist");
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public void kill() {
        logger.debug("kill ShadowsocksX");
        unixScriptRepository.execute("killall -9 ShadowsocksX");
    }

    @Override
    public void start() {
        logger.debug("start ShadowsocksX");
        unixScriptRepository.execute("open -a /Applications/ShadowsocksX.app/Contents/MacOS/ShadowsocksX");
    }

    @Override
    public void restart() {
        importConfig();
        kill();
        start();
        logger.info("重启Shadowsocks成功");
    }
}
