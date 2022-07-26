package com.liugroup.springboot_base.exception;

import com.liugroup.springboot_base.common.BaseResponse;
import com.liugroup.springboot_base.common.ErrorInfoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptonHandle {

    /**
     * 业务系统
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BaseBizException.class)
    public BaseResponse baseBizExceptionHandler(BaseBizException e) {
        if ((null != e.getErrorCode()) && (Integer.valueOf(e.getErrorCode()) == Integer.MAX_VALUE || Integer.valueOf(e.getErrorCode()) < 100000)) {
            log.error("系统异常", e);
            // 如果error_code 等于int最大值，或者小于100000 为rpc异常，统一处理为系统未知异常
            return new BaseResponse<String>(ErrorInfoEnum.ERROR_SYSTEM.getErrorCode(), ErrorInfoEnum.ERROR_SYSTEM.getErrorInfo(), null);
        } else {
            log.error("业务异常", e);
            return new BaseResponse<String>(e.getErrorCode(), e.getErrorInfo(), null);
        }
    }

    /**
     * json请求体不能为空
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error("业务异常", e);
        return new BaseResponse<String>(ErrorInfoEnum.ERROR_EMPTY_BODY.getErrorCode(), ErrorInfoEnum.ERROR_EMPTY_BODY.getErrorInfo(), null);
    }

    /**
     * 参数错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("业务异常", e);
        return new BaseResponse<String>(ErrorInfoEnum.ERROR_PARAMS.getErrorCode(), ErrorInfoEnum.ERROR_PARAMS.getErrorInfo(), null);
    }

    /**
     * 参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse constraintViolationExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.warn("Bad Request Parameters: dto entity [{}],field [{}],message [{}]",fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                    stringBuilder.append(fieldError.getDefaultMessage());
                });
            }
        }
        return new BaseResponse<String>(ErrorInfoEnum.ERROR_PARAMS.getErrorCode(), stringBuilder.toString(), null);
    }

    /**
     * 请求方式不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("业务异常", e);
        return new BaseResponse<String>(ErrorInfoEnum.ERROR_REQUEST_METOD_NO_SUP.getErrorCode(), ErrorInfoEnum.ERROR_REQUEST_METOD_NO_SUP.getErrorInfo(), null);
    }

    /**
     * 请求方式不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error("业务异常", e);
        return new BaseResponse<String>(ErrorInfoEnum.ERROR_REQUEST_METOD_NO_SUP.getErrorCode(), ErrorInfoEnum.ERROR_REQUEST_METOD_NO_SUP.getErrorInfo(), null);
    }

    /**
     * 系统异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public BaseResponse throwableHandler(Throwable e) {
        log.error("系统异常", e);
        return new BaseResponse<String>(ErrorInfoEnum.ERROR_SYSTEM.getErrorCode(), ErrorInfoEnum.ERROR_SYSTEM.getErrorInfo(), null);
    }

}
