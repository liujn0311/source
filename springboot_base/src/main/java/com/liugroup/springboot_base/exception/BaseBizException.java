package com.liugroup.springboot_base.exception;

import java.io.Serializable;

public class BaseBizException extends RuntimeException implements Serializable {

    /**
     * 错误码
     */
    private Integer errorCode;
    private String errorInfo;

    public BaseBizException() {

    }

    public BaseBizException(String s) { this(500,s);}

    public BaseBizException(Integer code, String s) {
        this.errorCode = code;
        this.errorInfo = s;
    }

    public BaseBizException(String s, Throwable throwable) {super(s,throwable);}

    public BaseBizException(Throwable throwable) {super(throwable);}

    public BaseBizException(String s, Throwable throwable, boolean b, boolean b1) {super(s,throwable,b,b1);}

    public Integer getErrorCode() {return errorCode;}

    public String getErrorInfo() {
        return errorInfo;
    }
}
