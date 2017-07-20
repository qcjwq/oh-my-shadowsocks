package com.dragon.shadowsocks.repository.impl;

import com.dragon.shadowsocks.repository.UnirestRepository;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by cjw on 2017/6/24.
 */
@Component
public class UnirestRepositoryImpl implements UnirestRepository {

    private static final Logger logger = LoggerFactory.getLogger(UnirestRepositoryImpl.class);

    @Override
    public String getString(String url) {
        try {
            logger.debug("url:" + url);
            Unirest.setTimeouts(10000, 10000);
            return Unirest.get(url).asString().getBody();
        } catch (UnirestException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
