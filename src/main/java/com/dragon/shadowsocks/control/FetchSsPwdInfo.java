package com.dragon.shadowsocks.control;

import com.dragon.shadowsocks.common.utils.BizException;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.contract.model.ProfileInfo;
import com.dragon.shadowsocks.domain.Profile;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by cjw on 2017/6/11.
 * 抓取Shadowsocks密码信息
 */
@RestController
public class FetchSsPwdInfo {
    private static final String IShadowUrl = "http://ss.ishadowx.com";

    @GetMapping("/getProfileList")
    public ProfileList.Response getProfileList() {
        ProfileList.Response response = new ProfileList.Response();

        try {
            String html = getHtml();
            if (StringUtils.isBlank(html)) {
                return response;
            }

            List<Profile> profileList = Profile.getProfileList(html);
            response.setProfileInfoList(convert(profileList));
        } catch (Exception e) {
            BizException ex = BizException.convert(e);
            response.getHead().setCode(ex.getErrorCode());
            response.getHead().setMessage(ex.getErrorMessage());
        }

        return response;
    }

    private String getHtml() {
        try {
            String timeSpan = String.valueOf(Calendar.getInstance().getTimeInMillis());
            String url = MessageFormat.format("{0}?timespan={1}", IShadowUrl, timeSpan);
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            throw new BizException(400, "获取IShadowURL失败", e.getCause());
        }
    }

    private List<ProfileInfo> convert(List<Profile> profileList) {
        if (profileList.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProfileInfo> profileInfoList = new ArrayList<>();
        for (Profile profile : profileList) {
            ProfileInfo profileInfo = new ProfileInfo();
            profileInfo.setServer(profile.getServer());
            profileInfo.setServer_port(profile.getServerPort());
            profileInfo.setPassword(profile.getPassword());
            profileInfo.setMethod(profile.getMethod());

            profileInfoList.add(profileInfo);
        }

        return profileInfoList;
    }
}
