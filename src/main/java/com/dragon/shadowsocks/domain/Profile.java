package com.dragon.shadowsocks.domain;

import com.dragon.shadowsocks.common.utils.BizException;
import com.dragon.shadowsocks.common.utils.PatternUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by cjw on 2017/6/11.
 */
public class Profile {
    private String password;
    private String method;
    private int serverPort;
    private String server;

    public static List<Profile> getProfileList(String html) {
        List<Profile> profileList = getServer(html);
        fillServerPort(profileList, html);
        fillPassword(profileList, html);
        fillMethod(profileList, html);

        return filterProfileList(profileList);
    }

    private static List<Profile> getServer(String html) {
        Pattern pattern = Pattern.compile(PatternUtil.ipAddressRegex);
        Matcher matcher = pattern.matcher(html);

        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(1));
        }

        List<Profile> profileList = new ArrayList<>();
        for (String aList : list) {
            Profile profile = new Profile();
            profile.setServer(aList);
            profileList.add(profile);
        }

        return profileList;
    }

    private static void fillServerPort(List<Profile> profileList, String html) {
        Pattern pattern = Pattern.compile(PatternUtil.portRegex);
        Matcher matcher = pattern.matcher(html);
        List<Integer> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(Integer.parseInt(matcher.group(1)));
        }

        if (profileList.size() != list.size()) {
            throw new BizException("ServerPort获取数量不匹配");
        }

        for (int i = 0; i < list.size(); i++) {
            Profile profile = profileList.get(i);
            profile.setServerPort(list.get(i));
        }
    }

    private static void fillPassword(List<Profile> profileList, String html) {
        Pattern pattern = Pattern.compile(PatternUtil.passwordRegex);
        Matcher matcher = pattern.matcher(html);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(1));
        }

        if (profileList.size() != list.size()) {
            throw new BizException("Password获取数量不匹配");
        }

        for (int i = 0; i < list.size(); i++) {
            Profile profile = profileList.get(i);
            profile.setPassword(list.get(i));
        }
    }

    private static void fillMethod(List<Profile> profileList, String html) {
        Pattern pattern = Pattern.compile(PatternUtil.methodRegex);
        Matcher matcher = pattern.matcher(html);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group(1));
        }

        if (profileList.size() != list.size()) {
            throw new BizException("Method获取数量不匹配");
        }

        for (int i = 0; i < list.size(); i++) {
            Profile profile = profileList.get(i);
            profile.setMethod(list.get(i));
        }
    }

    private static List<Profile> filterProfileList(List<Profile> profileList) {
        return profileList.stream().filter(p -> {
            if (p.getServerPort() <= 0) {
                return false;
            }

            if (StringUtils.isBlank(p.getPassword())) {
                return false;
            }

            MethodEnum[] values = MethodEnum.values();
            return Arrays.stream(values).anyMatch(m -> m.getValue().equals(p.getMethod()));
        }).collect(Collectors.toList());
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

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
