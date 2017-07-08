package com.dragon.shadowsocks.common.utils;

import com.alibaba.fastjson.JSON;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dragon.shadowsocks.model.macos.DataModel;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cjw on 2017/7/1.
 */
public final class PlistUtil {

    public static DataModel getProfileList(NSDictionary rootDict) {
        if (rootDict == null || !rootDict.containsKey("config")) {
            return null;
        }

        NSObject nsConfig = rootDict.objectForKey("config");
        String config = nsConfig.toXMLPropertyList();
        if (StringUtils.isBlank(config)) {
            return null;
        }

        Pattern pattern = Pattern.compile("<data>([\\w\\W]*)</data>");
        Matcher matcher = pattern.matcher(config);
        if (!matcher.find()) {
            return null;
        }

        String data = matcher.group(1).trim();
        String s = CommonUtil.decodeBase64(data);
        try {
            return JSON.parseObject(s, DataModel.class);
        } catch (Exception ignore) {
            return null;
        }
    }
}
