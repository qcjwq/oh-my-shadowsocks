package com.dragon.shadowsocks.domain;

/**
 * Created by cjw on 2017/6/11.
 * 加密方式枚举
 */
public enum MethodEnum {
    table("table"),
    rc4_md5("rc4-md5"),
    salsa20("salsa20"),
    chacha20("chacha20"),
    aes_256_cfb("aes-256-cfb"),
    aes_192_cfb("aes-192-cfb"),
    aes_128_cfb("aes-128-cfb"),
    bf_cfb("bf-cfb"),
    cast5_cfb("cast5-cfb"),
    des_cfb("des-cfb"),
    rc2_cfb("rc2-cfb"),
    rc4("rc4"),
    seed_cfb("seed-cfb");

    private String value;

    MethodEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
