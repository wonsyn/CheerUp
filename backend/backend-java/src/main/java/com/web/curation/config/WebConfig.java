package com.web.curation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.curation.util.JwtInterceptor;

@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private static final String[] EXCLUDE_PATHS = { "/user/login", "/user/signup","/user/checkid", "/swagger-resources/**", "/swagger-ui/**", "/v2/api-docs" };

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")// 기본 적용 경로
				.excludePathPatterns(EXCLUDE_PATHS);// 적용 제외 경로
//		registry.addInterceptor(udInterceptor).addPathPatterns("/**/update", "/**/delete"); // 기본 적용 경로
//        .excludePathPatterns("/**");// 적용 제외 경로
	}

//  Interceptor를 이용해서 처리하므로 전역의 Cross Origin 처리를 해준다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        default 설정.
//        Allow all origins.
//        Allow "simple" methods GET, HEAD and POST.
//        Allow all headers.
//        Set max age to 1800 seconds (30 minutes).
        registry.addMapping("/")
            .allowedOrigins("*")
//            .allowedOrigins("http://localhost:8080", "http://localhost:8081")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//            .allowedHeaders("*")
            .maxAge(6000);
    }

//    Swagger UI 실행시 404처리
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
        	    .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        
    }
}


















