package com.dragon.shadowsocks.model.macos;

import com.dragon.shadowsocks.contract.model.ProfileInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cjw on 2017/6/11.
 */
public class DataModel {
    private int current;
    private List<ProfileModel> profiles;

    public static List<ProfileModel> convert(List<ProfileInfo> profileInfoList) {
        if (profileInfoList == null || profileInfoList.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProfileModel> profileModelList = new ArrayList<>();
        profileInfoList.forEach(a -> {
            ProfileModel model = new ProfileModel();
            model.setServer(a.getServer());
            model.setServer_port(a.getServer_port());
            model.setPassword(a.getPassword());
            model.setMethod(a.getMethod());
            profileModelList.add(model);
        });

        return profileModelList;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<ProfileModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileModel> profiles) {
        this.profiles = profiles;
    }
}
