package com.dragon.shadowsocks.common.utils;

/**
 * Created by cjw on 2017/6/18.
 */
public class BizException extends RuntimeException {
    private int errorCode;
    private String errorMessage;
    private Throwable throwable;

    public BizException(String errorMessage) {
        this(500, errorMessage);
    }

    public BizException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public BizException(int errorCode, String errorMessage, Throwable throwable) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.throwable = throwable;
    }

    public static BizException convert(Exception e) {
        if (e instanceof BizException) {
            return (BizException) e;
        }

        return new BizException(500, e.getMessage());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        if (throwable == null) {
            return errorMessage;
        }

        return errorMessage;
    }
}
