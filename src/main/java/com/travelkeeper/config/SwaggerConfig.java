package com.travelkeeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * Swagger configuration
 *
 *  To test, run http://<host>:<port>/<context-root>/v2/api-docs
 *  To test swagger ui, run http://<host>:<port>/<context-root/swagger-ui.html
 *
 * Created by netocris on 29/08/2018
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.travelkeeper.controller";

    @Bean
    public Docket api(){
        return new Docket(SWAGGER_2)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .paths(any())
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title("Travel Keeper")
                .description("Keep your travel experience saved and ready to use")
                .termsOfServiceUrl("")
                .contact("")
                .license("")
                .licenseUrl("")
                .version("v0")
                .build();
    }

}
