package com.likelion.be.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition( // OpenAPI 정의
        info = @Info(title = "SWAGGER 실습", description = "6주차 스웨거 실습입니다", version = "v1"), // API 정보 정의
        servers = @Server(url = "http://localhost:8080", description = "서버 URL") // 서버 정보 정의
)
@RequiredArgsConstructor // 필수 생성자 주입
@Configuration // 스프링 설정 클래스
public class SwaggerConfig {

    @Bean // 스프링 빈 정의
    public GroupedOpenApi SwaggerOpenApi() {
        return GroupedOpenApi.builder()
                .group("Swagger-api") // API 그룹 정의
                .pathsToMatch("/api/**") // API 경로 매칭
                .build();
    }
}