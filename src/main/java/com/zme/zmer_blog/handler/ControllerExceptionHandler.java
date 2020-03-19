package com.zme.zmer_blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @authon timber
 * @description 拦截所有异常 并进行统一处理
 * @date 2020/2/7 下午6:36
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * @Author: timber
     * @Description:
     * @Date: 2020/2/8 上午2:09
     * @param request
     * @param e
     * @Return
     */
    // @ExceptionHandler 注解表示该方法可以处理异常
    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception{
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            // NotFoundException 会被拦截 但是我们希望将这个异常抛给 spring-boot自行处理
            // 所以这里要设置如果是带状态码的异常 该异常拦截类(ControllerExceptionHandler)不进行处理
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        // 设置返回页面
        mv.setViewName("error/error");
        return mv;

    }
}
