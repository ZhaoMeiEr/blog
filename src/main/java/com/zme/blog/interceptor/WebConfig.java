package com.zme.blog.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @authon timber
 * @description Web拦截配置
 * @date 2020/2/10 上午12:52
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * @Author: timber
     * @Description: 定义拦截路径
     * @Date: 2020/2/10 上午12:56
     * @param registry
     * @Return
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }

    /**
     * @Author: timber
     * @Description: 继承WebMvcConfigurationSupport类是会导致自动配置失效的 (WebMvcConfigurationAdapter被废除了)
     *               而自动配置一旦失效 静态资源就会加载不了
     *               此方法可以指定默认的静态资源的位置
     * @Date: 2020/2/10 上午1:37
     * @param registry
     * @Return
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * @Author: timber
     * @Description: 继承WebMvcConfigurationSupport类导致Controller无法注入Pageable
     *               此方法可以手动注册Spring data jpa pageable 的参数分解器
     * @Date: 2020/2/10 上午4:05
     * @param argumentResolvers
     * @Return
     */
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册Spring data jpa pageable 的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
}
