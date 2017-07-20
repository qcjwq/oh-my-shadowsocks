package com.dragon.shadowsocks.repository.impl;

import com.alibaba.fastjson.JSON;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dragon.shadowsocks.common.utils.CommonUtil;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.model.config.SchedulConfig;
import com.dragon.shadowsocks.model.macos.ProfileModel;
import com.dragon.shadowsocks.model.macos.ShadowsocksKeyEnum;
import com.dragon.shadowsocks.repository.PlistRepository;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import com.dragon.shadowsocks.repository.UnirestRepository;
import com.dragon.shadowsocks.repository.UnixScriptRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class ShadowSocksRepositoryImpl implements ShadowSocksRepository {
    private static final Logger logger = LoggerFactory.getLogger(ShadowSocksRepositoryImpl.class);
    private static int Index = 0;

    @Autowired
    private UnirestRepository unirestRepository;

    @Autowired
    private UnixScriptRepository unixScriptRepository;

    @Autowired
    private PlistRepository plistRepository;

    @Autowired
    private SchedulConfig schedulConfig;

    @Override
    public ProfileList.Response getProfileList() {
        String result = unirestRepository.getString(getProfileListUrl());
        return JSON.parseObject(result, ProfileList.Response.class);
    }

    @Override
    public int getIndex() {
        return Index;
    }

    @Override
    public void setIndex(int index) {
        Index = index;
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
        kill();
        open();
        shutdown();
        startup();
        logger.info("重启Shadowsocks成功");
    }

    /**
     * 设置Shadowsocks是否启动运行
     *
     * @param running the isRunning
     */
    private void setIsRunning(boolean running) {
        String plistPath = CommonUtil.getShadowSocksPlistPath();
        NSDictionary rootDict = plistRepository.getNSDictionary(plistPath);
        NSObject isRunning = new NSNumber(running);
        ProfileModel.putOrReplace(rootDict, ShadowsocksKeyEnum.ShadowsocksIsRunning, isRunning);
        plistRepository.saveAsBinary(rootDict, plistPath);

        importConfig();
    }

    private String getProfileListUrl() {
        String host = "106.15.89.193:7777";
        String profileHost = schedulConfig.getProfileHost();
        if (StringUtils.isNotBlank(profileHost)) {
            host = profileHost;
        }

        return MessageFormat.format("http://{0}/getProfileList", host);
    }
}
