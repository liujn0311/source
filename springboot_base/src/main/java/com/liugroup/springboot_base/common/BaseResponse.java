package com.liugroup.springboot_base.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer errorCode;

    private String errorInfo;

    private T data;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public BaseResponse() {
    }
    public BaseResponse(Integer errorCode){
        this.errorCode = errorCode;
    }

    public BaseResponse(Integer errorCode, String errorInfo, T data){
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.data = data;
    }

    //是否成功（自定义结果码为1000为成功）
    @JsonIgnore
    public boolean isSuccess(){
        return this.errorCode == 1000;
    }


    //成功时引用
    public static <T> BaseResponse<T> success(){
        return success(ErrorInfoEnum.YF_0000);
    }
    public static <T> BaseResponse<T> success(T data){
        return success(ErrorInfoEnum.YF_0000,data);
    }
    public static <T> BaseResponse<T> success(ErrorInfoEnum re){
        return success(re,null);
    }
    public static <T> BaseResponse<T> success(ErrorInfoEnum re, T data){
        Integer errorCode = re.getErrorCode();
        String errorInfo = re.getErrorInfo();
        return success(errorCode,errorInfo,data);
    }
    public static <T> BaseResponse<T> success(Integer errorCode, String errorInfo, T data){
        BaseResponse<T> result = new BaseResponse<>(1000);
        result.setErrorCode(errorCode);
        result.setErrorInfo(errorInfo);
        result.setData(data);
        return result;
    }



    //失败时引用

    public static <T> BaseResponse<T> fail(){
        return fail(ErrorInfoEnum.YF_9999);
    }
    public static <T> BaseResponse<T> fail(ErrorInfoEnum re){
        return fail(re,null);
    }
    public static <T> BaseResponse<T> fail(String errorInfo){
        return fail(ErrorInfoEnum.YF_9999.getErrorCode(),errorInfo,null);
    }
    public static <T> BaseResponse<T> fail(T data){
        return fail(ErrorInfoEnum.YF_9999,data);
    }
    public static <T> BaseResponse<T> fail(ErrorInfoEnum re, T data){
        Integer errorCode = re.getErrorCode();
        String errorInfo = re.getErrorInfo();
        return fail(errorCode,errorInfo,data);
    }
    public static <T> BaseResponse<T> fail(Integer errorCode , String errorInfo, T data){
        BaseResponse<T> result = new BaseResponse<>();
        result.setErrorCode(errorCode);
        result.setErrorInfo(errorInfo);
        result.setData(data);
        return result;
    }

}
