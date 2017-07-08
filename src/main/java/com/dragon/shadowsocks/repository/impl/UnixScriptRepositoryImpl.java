package com.dragon.shadowsocks.repository.impl;

import com.dragon.shadowsocks.repository.UnixScriptRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by cjw on 2017/7/1.
 */
@Component
public class UnixScriptRepositoryImpl implements UnixScriptRepository {

    private static final Logger logger = LoggerFactory.getLogger(UnixScriptRepositoryImpl.class);

    /**
     * 读取控制命令的输出结果
     *
     * @param process the process
     * @return 控制命令的输出结果 string
     */
    private static String readConsole(Process process) {
        StringBuilder cmdOut = new StringBuilder();
        InputStream fis = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line;
        try {
            while ((line = br.readLine()) != null) {
                cmdOut.append(line).append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        String result = cmdOut.toString().trim();
        if (StringUtils.isNotBlank(result)) {
            logger.debug(result);
        }

        return result;
    }

    /**
     * 执行 mac(unix) 脚本命令
     *
     * @param command the command
     * @return the int
     */
    @Override
    public int execute(String command) {
        String[] cmd = {"/bin/bash"};
        Runtime rt = Runtime.getRuntime();
        Process proc;
        try {
            proc = rt.exec(cmd);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return 1;
        }

        // 打开流
        OutputStream os = proc.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        try {
            bw.write(command);

            bw.flush();
            bw.close();

            //真奇怪，把控制台的输出打印一遍之后竟然能正常终止了
            readConsole(proc);

            //waitFor() 的作用在于 java 程序是否等待 Terminal 执行脚本完毕
            proc.waitFor();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        int retCode = proc.exitValue();
        if (retCode != 0) {
            readConsole(proc);
        }

        String message = "retCode：" + retCode;
        logger.debug(message);
        return retCode;
    }
}
