package com.dragon.shadowsocks.model.macos;

import com.alibaba.fastjson.JSON;
import com.dd.plist.NSData;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dragon.shadowsocks.common.utils.PlistUtil;

import java.util.List;

/**
 * Created by cjw on 2017/6/11.
 */
public class ProfileModel {
    private String password;
    private String method;
    private int server_port;
    private String server;
    private String remarks;

    /**
     * 根据Profiles更新rootDictionary
     *
     * @param rootDict  root dict
     * @param dataModel data model
     */
    public static void updateNSDictionary(NSDictionary rootDict, DataModel dataModel) {
        ProfileModel currentProfile = dataModel.getProfiles().get(dataModel.getCurrent());

        //server
        NSString server = new NSString(currentProfile.getServer());
        putOrReplace(rootDict, ShadowsocksKeyEnum.ProxyIp, server);

        //port
        NSString port = new NSString(String.valueOf(currentProfile.getServer_port()));
        putOrReplace(rootDict, ShadowsocksKeyEnum.ProxyPort, port);

        //password
        NSString password = new NSString(currentProfile.getPassword());
        putOrReplace(rootDict, ShadowsocksKeyEnum.ProxyPassword, password);

        //mothod
        NSString method = new NSString(currentProfile.getMethod());
        putOrReplace(rootDict, ShadowsocksKeyEnum.ProxyEncryption, method);

        //config
        NSData config = new NSData(JSON.toJSONBytes(dataModel));
        putOrReplace(rootDict, ShadowsocksKeyEnum.Config, config);
    }

    /**
     * 获取正在使用中的配置
     *
     * @param rootDict root dict
     * @return profile
     */
    private static ProfileModel getUsingProfileModel(NSDictionary rootDict) {
        DataModel usingDataModel = PlistUtil.getProfileList(rootDict);
        if (usingDataModel == null) {
            return null;
        }

        int current = usingDataModel.getCurrent();
        List<ProfileModel> profileList = usingDataModel.getProfiles();

        if (profileList == null || profileList.isEmpty()) {
            return null;
        }

        return profileList.get(current);
    }

    /**
     * 是否需要更新配置
     *
     * @param rootDict  root dict
     * @param dataModel data model
     * @return boolean
     */
    public static boolean isNeedUpdateConfig(NSDictionary rootDict, DataModel dataModel) {
        ProfileModel usingProfileModel = ProfileModel.getUsingProfileModel(rootDict);
        ProfileModel profileModel = dataModel.getProfiles().get(dataModel.getCurrent());
        return !profileModel.equals(usingProfileModel);
    }

    private static String getNSDcitValue(NSDictionary rootDict, ShadowsocksKeyEnum key) {
        if (!rootDict.containsKey(key.getValue())) {
            return "";
        }

        return rootDict.get(key.getValue()).toString();
    }

    private static void putOrReplace(NSDictionary rootDict, ShadowsocksKeyEnum key, NSObject value) {
        if (!rootDict.containsKey(key.getValue())) {
            rootDict.put(key.getValue(), value);
            return;
        }

        rootDict.replace(key.getValue(), value);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getServer_port() {
        return server_port;
    }

    public void setServer_port(int server_port) {
        this.server_port = server_port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileModel that = (ProfileModel) o;

        if (server_port != that.server_port) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (server != null ? !server.equals(that.server) : that.server != null) return false;
        return remarks != null ? remarks.equals(that.remarks) : that.remarks == null;
    }

    @Override
    public int hashCode() {
        int result = password != null ? password.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + server_port;
        result = 31 * result + (server != null ? server.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        return result;
    }
}
