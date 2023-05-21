package com.zhonghui.common.core.config;

import com.zhonghui.common.core.exception.BadRequestException;
import com.zhonghui.common.core.exception.BusinessException;
import com.zhonghui.common.core.result.FwResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 异常处理
 * @author zhonghui
 * @date 2020-12-06
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public FwResult HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,HttpServletResponse response) {
        response.setStatus(405);
        log.error("HttpRequestMethodNotSupportedException 异常",e);
        return FwResult.failedMsg(405,"请求方法不对");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({BusinessException.class})
    public FwResult businessException(BusinessException e) {
        log.error("BusinessException 异常",e);
        return FwResult.failedMsg("系统内部错误,请稍后重试");
    }
    @ExceptionHandler({AccessDeniedException.class})
    public FwResult AccessDeniedException(AccessDeniedException e,HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        log.error("AccessDeniedException 异常",e);
        return FwResult.failedMsg(403,"权限不足");
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public FwResult MethodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletResponse response) {
        log.error("MethodArgumentNotValidException 异常",e);
        response.setStatus(422);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return FwResult.failedMsg(422, message);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public FwResult HttpMessageNotReadableException(HttpMessageNotReadableException e,HttpServletResponse response) {
        log.error("HttpMessageNotReadableException 异常",e);
        response.setStatus(422);
        return FwResult.failedMsg(422, "参数不足，参数有误");
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = BadRequestException.class)
    public FwResult badRequestException(BadRequestException e, HttpServletResponse response) {
        // 打印堆栈信息
        log.error("BadRequestException 异常",e);
        response.setStatus(e.getCode());
        return FwResult.failedMsg(e.getCode(),e.getMessage());
    }
    /**
     * 全局异常捕捉处理
     *
     * @param ex .
     * @return .
     */
    @ExceptionHandler(value = Exception.class)
    public FwResult errorHandler(Exception ex, HttpServletRequest request)
    {
        if (null != request) {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append("global catch reqUrl:");
            strBuild.append(request.getRequestURI());
            if (HttpMethod.GET.matches(request.getMethod())) {
                strBuild.append(" queryString:");
                strBuild.append(request.getQueryString());
            }
            log.error(strBuild.toString(), ex);
            return FwResult.failedMsg("系统内部错误,请稍后重试");
        }
        else
        {
            log.error("global catch", ex);
            return FwResult.failedMsg("系统内部错误,请稍后重试");
        }
    }

}
