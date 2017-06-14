package com.dragon.shadowsocks.control;

import com.dragon.shadowsocks.domain.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cjw on 2017/6/11.
 */
@RestController
public class CacheSsPwdInfo {

    private static List<Profile> profileList;

}
