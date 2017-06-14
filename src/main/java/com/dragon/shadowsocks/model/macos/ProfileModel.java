package com.dragon.shadowsocks.model.macos;

/**
 * Created by cjw on 2017/6/11.
 */
public class ProfileModel {
    private String password;
    private String method;
    private int server_port;
    private String server;

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

    public int getServer_port() {
        return server_port;
    }

    public void setServer_port(int server_port) {
        this.server_port = server_port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
