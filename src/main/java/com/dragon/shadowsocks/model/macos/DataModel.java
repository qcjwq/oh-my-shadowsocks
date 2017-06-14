package com.dragon.shadowsocks.model.macos;

import java.util.List;

/**
 * Created by cjw on 2017/6/11.
 */
public class DataModel {
    private int current;
    private List<ProfileModel> profileModels;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<ProfileModel> getProfileModels() {
        return profileModels;
    }

    public void setProfileModels(List<ProfileModel> profileModels) {
        this.profileModels = profileModels;
    }
}
