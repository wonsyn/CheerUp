package com.web.curation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            // @ApiSelectorBuilder
	            .select()
	            // @ApiSelectorBuilder
	            .apis(RequestHandlerSelectors.basePackage("com.web.curation.controller"))
	            // @ApiSelectorBuilder
	            .paths(PathSelectors.any())
	            // Docket
	            .build()
	            .apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
	    
	    // 빌더패턴 : 밖에서 많이 씀
	    return new ApiInfoBuilder()
	            .title("CheerUpAPI")
	            .description("B104 CheerUp 취업 API 명세서")
	            .version("v01.220731")
	            .build();
	}
	

	
}


