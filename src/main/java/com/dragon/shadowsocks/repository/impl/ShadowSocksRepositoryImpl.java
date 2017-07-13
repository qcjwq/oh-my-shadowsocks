package com.dragon.shadowsocks.repository.impl;

import com.alibaba.fastjson.JSON;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSSet;
import com.dragon.shadowsocks.common.utils.CommonUtil;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.model.macos.ProfileModel;
import com.dragon.shadowsocks.model.macos.ShadowsocksKeyEnum;
import com.dragon.shadowsocks.repository.PlistRepository;
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

    @Autowired
    private PlistRepository plistRepository;

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
    public void shutdown() {
        logger.debug("关闭ShadowsocksX");
        setIsRunning(false);
    }

    @Override
    public void kill() {
        logger.debug("kill ShadowsocksX");
        unixScriptRepository.execute("killall -9 ShadowsocksX");
    }

    @Override
    public void open() {
        logger.debug("open ShadowsocksX");
        unixScriptRepository.execute("open -a /Applications/ShadowsocksX.app/Contents/MacOS/ShadowsocksX");
    }

    @Override
    public void startup() {
        logger.debug("打开ShadowsocksX");
        setIsRunning(true);
    }

    @Override
    public void restart() {
        shutdown();
        kill();
        open();
        startup();
        logger.info("重启Shadowsocks成功");
    }

    /**
     * 设置Shadowsocks是否启动运行
     *
     * @param ordered the ordered
     */
    private void setIsRunning(boolean ordered) {
        String plistPath = CommonUtil.getShadowSocksPlistPath();
        NSDictionary rootDict = plistRepository.getNSDictionary(plistPath);
        NSSet isRunning = new NSSet(ordered);
        ProfileModel.putOrReplace(rootDict, ShadowsocksKeyEnum.ShadowsocksIsRunning, isRunning);
        plistRepository.saveAsXML(rootDict, plistPath);

        importConfig();
    }
}
