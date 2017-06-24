package com.dragon.shadowsocks.repository.impl;

import com.alibaba.fastjson.JSON;
import com.dragon.shadowsocks.contract.ProfileList;
import com.dragon.shadowsocks.repository.ShadowSocksRepository;
import com.dragon.shadowsocks.repository.UnirestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class ShadowSocksRepositoryImpl implements ShadowSocksRepository {
    private static final String Url = "http://106.15.89.193:7777/getProfileList";

    @Autowired
    private UnirestRepository unirestRepository;

    @Override
    public ProfileList.Response getProfileList() {
        String result = unirestRepository.getString(Url);
        return JSON.parseObject(result, ProfileList.Response.class);
    }
}
