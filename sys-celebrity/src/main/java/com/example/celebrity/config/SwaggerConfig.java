package com.example.celebrity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket(Environment e) {
        // 获取项目环境：
        Profiles profiles = Profiles.of("dev");
        // 通过environment.acceptsProfiles 判断是否处在设定的环境当中
        boolean flag = e.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.celebrity.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xiaopang", "http://www.xiaopang.com", "1439972069@qq.com");
        return new ApiInfoBuilder()
                .title("网红swagger文档")
                .description("文档描述")
                .contact(contact)   // 联系方式
                .version("1.1.0")  // 版本
                .build();
    }
}
