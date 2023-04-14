package com.zzx.zzx_music_recommendation_system.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/10 12:42
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("swagger2接口文档")
                .version("1.0")
                .build();
    }


    @Bean
    public Docket configure() {
        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zzx.zzx_music_recommendation_system.controller"))
                .paths(PathSelectors.any()).build();
    }
}
