package com.jacob.common.exception;

import com.jacob.common.model.base.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理程序
 * 统一拦截处理
 */

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理Validated验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    public ResponseVO<?> bindExceptionHandler(BindException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        log.error("BindException：", e);
        return ResponseVO.error(objectError.getDefaultMessage());
    }

    /**
     * 处理单个文件超大异常
     *
     * @param e
     * @return
     * @ExceptionHandler 注解用来指明异常的处理类型
     */
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseVO<?> fileSizeLimitExceededExceptionHandler(FileSizeLimitExceededException e) {
        log.error("FileSizeLimitExceededException异常：", e);
        return ResponseVO.error("单个文件大小不允许超过2MB");
    }

    /**
     * 处理请求数据超大异常
     *
     * @param e
     * @return
     * @ExceptionHandler
     */
    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseVO<?> sizeLimitExceededExceptionHandler(SizeLimitExceededException e) {
        log.error("SizeLimitExceededException异常：", e);
        return ResponseVO.error("请求数据大小不允许超过10MB");
    }

    /**
     * 处理NullPointerException异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({NullPointerException.class})
    public ResponseVO<?> customExceptionHandler(NullPointerException e) {
        log.error("NullPointerException异常：", e);
        return ResponseVO.error("空指针异常");
    }


    /**
     * 处理其它异常：统一捕获，写入异常日志
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseVO<?> exceptionHandler(Exception e) {
        log.error("Exception异常：", e);
        return ResponseVO.error();
    }

}
