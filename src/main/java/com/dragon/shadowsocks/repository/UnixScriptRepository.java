package com.dragon.shadowsocks.repository;

/**
 * Created by cjw on 2017/7/1.
 */
public interface UnixScriptRepository {

    /**
     * 执行 mac(unix) 脚本命令
     *
     * @param command the command
     * @return the int
     */
    int execute(String command);

}
