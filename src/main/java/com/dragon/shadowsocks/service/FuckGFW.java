package com.dragon.shadowsocks.service;

import com.dragon.shadowsocks.common.utils.Utils;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.contract.model.ProfileInfo;
import com.dragon.shadowsocks.model.macos.DataModel;
import com.dragon.shadowsocks.model.macos.ProfileModel;
import com.dragon.shadowsocks.repository.PlistRepository;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class FuckGFW implements CommandLineRunner {

    @Autowired
    private ShadowSocksRepository shadowSocksRepository;

    @Autowired
    private PlistRepository plistRepository;

    public void run() {
        //1、获取种子账号密码

        //2、操作Plist，写入文件

        //3、重启应用

        //4、验证

    }

    public void run(String... strings) throws Exception {
        ProfileList.Response profileList = shadowSocksRepository.getProfileList();

        List<ProfileInfo> profileInfoList = profileList.getProfileInfoList();
        if (profileInfoList.isEmpty()) {
            return;
        }

        DataModel dataModel = new DataModel();
        dataModel.setCurrent(0);
        List<ProfileModel> profileModelList = DataModel.convert(profileInfoList);
        dataModel.setProfiles(profileModelList);

        plistRepository.updateConfig(Utils.getShadowSocksPlistPath(), dataModel);
    }
}
