package com.zme.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @authon timber
 * @description 自定义异常-找不到资源
 * @date 2020/2/8 上午3:19
 */
// @ResponseStatus 注解指定响应的状态码 HttpStatus.NOT_FOUND代表404
// spring-boot 捕获到这个状态码会找到404.html 进行跳转
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
