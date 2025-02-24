package com.hwk9407.bookmanagementassignment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("도서 관리 시스템 CRUD 과제")
                        .version("1.0.0")
                        .description("도서와 저자 정보를 관리하는 간단한 시스템 구현 목표")
                        .contact(new Contact()
                                .name("허원경")
                                .email("hwk__@naver.com"))
                );
    }
}
