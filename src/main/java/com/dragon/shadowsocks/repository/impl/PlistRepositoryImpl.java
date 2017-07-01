package com.dragon.shadowsocks.repository.impl;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.dragon.shadowsocks.common.utils.BizException;
import com.dragon.shadowsocks.common.utils.PlistUtil;
import com.dragon.shadowsocks.model.macos.DataModel;
import com.dragon.shadowsocks.model.macos.ProfileModel;
import com.dragon.shadowsocks.repository.PlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class PlistRepositoryImpl implements PlistRepository {
    private static final Logger logger = LoggerFactory.getLogger(PlistRepositoryImpl.class);

    /**
     * 获取Plist文件字典集合
     *
     * @param plistPath plist path
     * @return NSDictionary
     */
    @Override
    public NSDictionary getNSDictionary(String plistPath) {
        File file = new File(plistPath);
        if (!file.exists()) {
            return new NSDictionary();
        }

        try {
            return (NSDictionary) PropertyListParser.parse(file);
        } catch (Exception e) {
            throw new BizException(500, "转换PList文件失败", e);
        }
    }

    /**
     * 获取ShadowSocks中config配置
     *
     * @param plistPath plist path
     * @return dataModel
     */
    @Override
    public DataModel getProfileList(String plistPath) {
        NSDictionary rootDict = getNSDictionary(plistPath);
        return getProfileList(rootDict);
    }

    /**
     * 获取ShadowSocks中config配置
     *
     * @param rootDict root dict
     * @return dataModel
     */
    @Override
    public DataModel getProfileList(NSDictionary rootDict) {
        return PlistUtil.getProfileList(rootDict);
    }

    /**
     * 更新Shadowsocks中config配置
     *
     * @param plistPath plist path
     * @param dataModel datamodel
     */
    @Override
    public void updateConfig(String plistPath, DataModel dataModel) {
        if (dataModel == null) {
            return;
        }

        int current = dataModel.getCurrent();
        List<ProfileModel> profiles = dataModel.getProfiles();
        if (profiles == null || profiles.isEmpty() || current < 0 || current > profiles.size()) {
            return;
        }

        //获取
        NSDictionary rootDict = getNSDictionary(plistPath);
        if (!ProfileModel.isNeedUpdateConfig(rootDict, dataModel)) {
            logger.debug("不需要更新配置");
            return;
        }

        //更新正在使用的Profile配置值
        ProfileModel.updateNSDictionary(rootDict, dataModel);

        //保存更新后的对象到文件
        saveAsBinary(rootDict, plistPath);
    }

    /**
     * 保存NSDictionary对象到PList文件
     *
     * @param rootDictionary root dictionary
     * @param filePath       file path
     */
    @Override
    public void saveAsXML(NSDictionary rootDictionary, String filePath) {
        try {
            PropertyListParser.saveAsXML(rootDictionary, new File(filePath));
        } catch (IOException e) {
            throw new BizException(500, "保存Plist文件失败", e);
        }
    }

    /**
     * 保存NSDictionary对象到PList文件
     *
     * @param rootDictionary root dictionary
     * @param filePath       file path
     */
    @Override
    public void saveAsBinary(NSDictionary rootDictionary, String filePath) {
        try {
            PropertyListParser.saveAsBinary(rootDictionary, new File(filePath));
            logger.debug(rootDictionary.toXMLPropertyList());
            logger.info(MessageFormat.format("更新{0}成功", filePath));
        } catch (IOException e) {
            throw new BizException(500, "保存Plist文件失败", e);
        }
    }
}
