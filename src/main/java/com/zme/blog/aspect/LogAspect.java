package com.zme.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @authon timber
 * @description 日志切面
 * @date 2020/2/8 上午3:20
 */
@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);
    /**
     * @Author: timber
     * @Description: 切面定义
     * @Date: 2020/2/8 上午3:22
     * @param
     * @Return
     */
    // @Pointcut() 注解定义某个方法为一个切面
    // execution() 规定拦截哪些类
    // * com.zme.blog.web.*.*(..) 表示 com.zme.blog.web 包下的所有类 下的 任意参数的 所有方法
    @Pointcut("execution(* com.zme.blog.web.*.*(..))")
    public void log(){}

    /**
     * @Author: timber
     * @Description: 在被拦截方法执行之前 执行
     *               记录请求数据日志
     * @Date: 2020/2/8 上午3:34
     * @param
     * @Return
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        // joinPoint.getSignature().getDeclaringTypeName() 获取被拦截方法的全包名
        // joinPoint.getSignature().getName() 获取被拦截方法的方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // joinPoint.getArgs() 获取被拦截方法的参数
        Object[] args = joinPoint.getArgs();
        logger.info("Request {}", new RequestLog(url, ip, classMethod, args));

    }

    /**
     * @Author: timber
     * @Description: 在被拦截方法执行之后 执行
     * @Date: 2020/2/8 上午3:35
     * @param
     * @Return
     */
    @After("log()")
    public void doAfter(){}

    /**
     * @Author: timber
     * @Description: 记录被拦截方法的返回值
     *               记录方法返回值日志
     * @Date: 2020/2/8 上午3:37
     * @param
     * @Return
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}", result);
    }

    public class RequestLog{
        // 请求url
        private String url;
        // 请求ip
        private String ip;
        // 请求方法
        private String classMethon;
        // 请求参数
        private Object[] args;

        /**
         * @authon timber
         * @description 请求日志对象
         * @date 2020/2/8 上午3:20
         */
        public RequestLog(String url, String ip, String classMethon, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethon = classMethon;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethon='" + classMethon + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
