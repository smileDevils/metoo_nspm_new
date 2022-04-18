package com.metoo.monitor.core.config.multipart;

import com.metoo.monitor.core.utils.ResponseUtil;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseBody
    public Object fileSizeLimitExceededException(FileSizeLimitExceededException e){
        logger.error(e.getMessage(),e);
        return ResponseUtil.badArgument("文件最大不允许超过100M");
    }

}
