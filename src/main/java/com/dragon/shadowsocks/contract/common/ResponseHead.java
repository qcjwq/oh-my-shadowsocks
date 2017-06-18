package com.dragon.shadowsocks.contract.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by cjw on 2017/6/18.
 */
public class ResponseHead {
    private int code = 200;
    private String message = "success";
    private Date timeStamp = new Date();
    private int interval;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
