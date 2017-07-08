package com.dragon.shadowsocks.repository;

import com.dd.plist.NSDictionary;
import com.dragon.shadowsocks.model.macos.DataModel;

/**
 * Created by cjw on 2017/6/24.
 */
public interface PlistRepository {

    /**
     * 获取Plist文件字典集合
     *
     * @param plistPath plist path
     * @return NSDictionary
     */
    NSDictionary getNSDictionary(String plistPath);

    /**
     * 获取ShadowSocks中config配置
     *
     * @param plistPath plist path
     * @return dataModel
     */
    DataModel getProfileList(String plistPath);

    /**
     * 获取ShadowSocks中config配置
     *
     * @param nsDictionary ns dictionary
     * @return data model
     */
    DataModel getProfileList(NSDictionary nsDictionary);

    /**
     * 是否需要更新Shadowsocks的配置
     *
     * @param plistPath plist path
     * @param dataModel data model
     * @return boolean
     */
    boolean isNeedUpdateConfig(String plistPath, DataModel dataModel);

    /**
     * 更新Shadowsocks中config配置
     *
     * @param plistPath plist path
     * @param dataModel datamodel
     */
    void updateConfig(String plistPath, DataModel dataModel);

    /**
     * 保存NSDictionary对象到PList文件
     *
     * @param rootDictionary root dictionary
     * @param filePath       file path
     */
    void saveAsXML(NSDictionary rootDictionary, String filePath);

    /**
     * 保存NSDictionary对象到PList文件
     *
     * @param rootDictionary root dictionary
     * @param filePath       file path
     */
    void saveAsBinary(NSDictionary rootDictionary, String filePath);
}
