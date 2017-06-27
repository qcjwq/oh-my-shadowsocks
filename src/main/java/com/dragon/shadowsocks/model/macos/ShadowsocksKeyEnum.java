package com.dragon.shadowsocks.model.macos;

/**
 * Created by cjw on 2017/6/25.
 * Shadowsocks Plist文件中key值枚举
 */
public enum ShadowsocksKeyEnum {

    ShadowsocksIsRunning {
        @Override
        public String getValue() {
            return "ShadowsocksIsRunning";
        }
    },

    ShadowsocksMode {
        @Override
        public String getValue() {
            return "ShadowsocksMode";
        }
    },

    Config {
        @Override
        public String getValue() {
            return "config";
        }
    },

    ProxyEncryption {
        @Override
        public String getValue() {
            return "proxy encryption";
        }
    },

    ProxyIp {
        @Override
        public String getValue() {
            return "proxy ip";
        }
    },

    ProxyPassword {
        @Override
        public String getValue() {
            return "proxy password";
        }
    },

    ProxyPort {
        @Override
        public String getValue() {
            return "proxy port";
        }
    },

    PublicServer {
        @Override
        public String getValue() {
            return "public server";
        }
    };

    public abstract String getValue();
}
