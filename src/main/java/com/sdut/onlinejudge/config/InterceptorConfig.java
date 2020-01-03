package com.sdut.onlinejudge.config;

import com.sdut.onlinejudge.Interceptor.MainInterceptor;
import com.sdut.onlinejudge.utils.TokenConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: Devhui
 * @Date: 2019-12-05 20:44
 * @Version 1.0
 */

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Autowired
    private MainInterceptor mainInterceptor;

    // 前后端分离项目
    // 配置了跨域后，访问正常，但是配置了拦截器以后，有的访问正常，有的出现跨域问题无法获取header 拿到token
    // 发现出现跨域问题的都是拦截器里面没有放行的请求。
    // 改用过滤器CorsFilter 来配置跨域，由于Filter的位置是在Interceptor之前的，问题得到解决
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许跨域请求的域名
        config.addAllowedOrigin("*");
        // 是否允许证书 不再默认开启
        // config.setAllowCredentials(true);
        // 设置允许的方法
        config.addAllowedMethod("*");
        // 允许任何头
        config.addAllowedHeader("*");
        config.addExposedHeader(TokenConstant.HEADER_TOKEN);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        System.out.println("我是拦截器");
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则，/**表示拦截所有请求
        // excludePathPatterns 用户排除拦截
        // ****注意前面别掉了斜杠****
        registry.addInterceptor(mainInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/standing", "/contest/all", "/user/reg", "/user/feedback"
                        , "/admin/**/**");
        super.addInterceptors(registry);
    }

}
