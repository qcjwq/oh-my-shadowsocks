package com.dragon.shadowsocks.control;

import com.dragon.shadowsocks.domain.Profile;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cjw on 2017/6/11.
 * 抓取Shadowsocks密码信息
 */
@RestController
public class FetchSsPwdInfo {

    private static final String url = "http://ss.ishadowx.com/";

    @GetMapping("/getProfileList")
    public List<Profile> getProfileList() {
        String html = getHtml();
        if (StringUtils.isBlank(html)) {
            return Collections.emptyList();
        }

        Pattern pattern = Pattern.compile("IP Address:<span id=\"ipjpb\">b.jpip.pro</span> ");
        pattern = Pattern.compile("<title>([^</title>]*)");

        List<Profile> profileList = new ArrayList<>();


        return profileList;

    }

    private String getHtml() {
        try {
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }


}
