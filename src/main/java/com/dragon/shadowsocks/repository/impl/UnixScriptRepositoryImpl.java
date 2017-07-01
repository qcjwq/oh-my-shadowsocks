package com.dragon.shadowsocks.repository.impl;

import com.dragon.shadowsocks.repository.UnixScriptRepository;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by cjw on 2017/7/1.
 */
@Component
public class UnixScriptRepositoryImpl implements UnixScriptRepository {

    /**
     * 读取控制命令的输出结果
     *
     * @param process the process
     * @return 控制命令的输出结果 string
     */
    private static String readConsole(Process process) {
        StringBuffer cmdOut = new StringBuffer();
        InputStream fis = process.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                cmdOut.append(line).append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("执行系统命令后的控制台输出为：\n" + cmdOut.toString());
        return cmdOut.toString().trim();
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
        Process proc = null;
        try {
            proc = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 打开流
        OutputStream os = proc.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        try {
            bw.write(command);

            bw.flush();
            bw.close();

            /** 真奇怪，把控制台的输出打印一遍之后竟然能正常终止了~ */
            readConsole(proc);

            /** waitFor() 的作用在于 java 程序是否等待 Terminal 执行脚本完毕~ */
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int retCode = proc.exitValue();

        System.err.println("unix script retCode = " + retCode);

        if (retCode != 0) {
            readConsole(proc);
            System.err.println("UnixScriptUil.execute 出错了!!");
        }
        return retCode;
    }
}
