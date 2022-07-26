package com.liugroup.springboot_base.common;

public enum ErrorInfoEnum {

    YF_0000(200, "成功"),

    ERROR_EMPTY_BODY(1111, "参数不能为空"),

    ERROR_PARAMS(1112, "参数错误"),

    ERROR_SYSTEM(9999, "系统异常"),

    ERROR_REQUEST_METOD_NO_SUP(405, "请求方式不支持"),

    ERROR_FILE_MAX(10002, "图片最大支持500KB"),

    YF_9999(9999,"系统异常");




    private Integer errorCode;
    private String errorInfo;

    ErrorInfoEnum(Integer errorCode , String errorInfo){
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

}
