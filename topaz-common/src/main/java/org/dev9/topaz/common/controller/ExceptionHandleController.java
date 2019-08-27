package org.dev9.topaz.common.controller;

import org.dev9.topaz.common.exception.PageNotFoundException;
import org.dev9.topaz.common.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandleController {
    private final static Logger logger= LoggerFactory.getLogger(ExceptionHandleController.class);

    @ExceptionHandler({PageNotFoundException.class})
    public String handle(HttpServletRequest request,
                         HttpServletResponse response,
                         Exception e){
        logger.error("统一异常: "+e.toString());
        request.setAttribute("excaption", e);

        if (e instanceof PageNotFoundException)
            return "404";
        if (e instanceof UnauthorizedException)
            return "403";
        return "500";
    }
}
