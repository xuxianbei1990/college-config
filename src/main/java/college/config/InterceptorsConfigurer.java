package college.config;

import college.interceptor.ParamsHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Name  web配置
 *
 * @author xuxb
 * Date 2018-12-08
 * VersionV1.0
 * @description
 */
@Configuration
@EnableWebMvc
public class InterceptorsConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ParamsHandlerInterceptor());
    }

}
