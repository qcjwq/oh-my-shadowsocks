package com.dragon.shadowsocks.service;

import com.dragon.shadowsocks.common.utils.CommonUtil;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.contract.model.ProfileInfo;
import com.dragon.shadowsocks.model.macos.DataModel;
import com.dragon.shadowsocks.model.macos.ProfileModel;
import com.dragon.shadowsocks.repository.PlistRepository;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by cjw on 2017/7/8.
 */
public class ScheduledExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledExecutor.class);

    private ShadowSocksRepository shadowSocksRepository;
    private PlistRepository plistRepository;

    public ScheduledExecutor(ShadowSocksRepository shadowSocksRepository, PlistRepository plistRepository) {
        this.shadowSocksRepository = shadowSocksRepository;
        this.plistRepository = plistRepository;
    }

    @Override
    public void run() {
        if (!shadowSocksRepository.isRunning()) {
            shadowSocksRepository.start();
        }

        List<ProfileInfo> profileInfoList = getProfileInfos();
        if (profileInfoList.isEmpty()) {
            logger.warn("获取远程配置信息为空！");
            return;
        }

        DataModel dataModel = createDataModel(profileInfoList);

        String plistPath = CommonUtil.getShadowSocksPlistPath();
        if (!plistRepository.isNeedUpdateConfig(plistPath, dataModel)) {
            logger.debug("不需要更新配置");
            return;
        }

        plistRepository.updateConfig(plistPath, dataModel);
        shadowSocksRepository.restart();
        logger.info("更新配置成功");
    }

    private List<ProfileInfo> getProfileInfos() {
        ProfileList.Response profileList = shadowSocksRepository.getProfileList();
        if (profileList == null) {
            return Collections.emptyList();
        }

        List<ProfileInfo> profileInfoList = profileList.getProfileInfoList();
        if (profileInfoList.isEmpty()) {
            return Collections.emptyList();
        }

        return profileInfoList;
    }

    private DataModel createDataModel(List<ProfileInfo> profileInfoList) {
        DataModel dataModel = new DataModel();
        //todo:根据算法选择哪个服务器
        dataModel.setCurrent(5);
        List<ProfileModel> profileModelList = DataModel.convert(profileInfoList);
        dataModel.setProfiles(profileModelList);
        return dataModel;
    }
}
