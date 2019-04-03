package com.wish.common.exception;

import com.wish.common.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * 全局异常处理
 */

@RestControllerAdvice
public class DefaultExceptionHandler {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler
    @ResponseBody
    public Object handleException(Exception e) {
        e.printStackTrace();
        String msg = e.getMessage();
        return JsonUtil.genError(msg);
    }

}
