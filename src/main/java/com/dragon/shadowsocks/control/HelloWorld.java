package com.dragon.shadowsocks.control;

import com.dragon.shadowsocks.common.utils.Utils;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.contract.model.ProfileInfo;
import com.dragon.shadowsocks.model.macos.DataModel;
import com.dragon.shadowsocks.model.macos.ProfileModel;
import com.dragon.shadowsocks.repository.PlistRepository;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import com.dragon.shadowsocks.repository.UnixScriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by cjw on 2017/6/11.
 */
@RestController
public class HelloWorld {

    @Autowired
    private ShadowSocksRepository shadowSocksRepository;

    @Autowired
    private PlistRepository plistRepository;

    @Autowired
    private UnixScriptRepository unixScriptRepository;

    @GetMapping("/say")
    public String say() {
        return "hello world";
    }


    @RequestMapping("/test")
    public void test() {
        ProfileList.Response profileList = shadowSocksRepository.getProfileList();

        List<ProfileInfo> profileInfoList = profileList.getProfileInfoList();
        if (profileInfoList.isEmpty()) {
            return;
        }

        DataModel dataModel = new DataModel();
        dataModel.setCurrent(5);
        List<ProfileModel> profileModelList = DataModel.convert(profileInfoList);
        dataModel.setProfiles(profileModelList);

        plistRepository.updateConfig(Utils.getShadowSocksPlistPath(), dataModel);

        unixScriptRepository.execute("defaults import clowwindy.ShadowsocksX $HOME/Library/Preferences/clowwindy.ShadowsocksX.plist");

        unixScriptRepository.execute("killall -9 ShadowsocksX");

        unixScriptRepository.execute("open -a /Applications/ShadowsocksX.app/Contents/MacOS/ShadowsocksX");

        out.println("success");
    }
}
