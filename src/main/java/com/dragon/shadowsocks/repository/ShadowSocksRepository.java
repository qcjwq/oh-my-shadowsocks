package com.dragon.shadowsocks.repository;

import com.dragon.shadowsocks.contract.ProfileList;

/**
 * Created by cjw on 2017/6/24.
 */
public interface ShadowSocksRepository {
    ProfileList.Response getProfileList();
}
