package com.carero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String API_TITLE = "Carero API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Carero API 명세서";
//    private static final String API_LICENSE = "팀 케어로";
//    private static final String API_LICENSE_URL = "";

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.carero.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
//                .license(API_LICENSE)
//                .licenseUrl("http://daddyprogrammer.org")
                .build();
    }
}