package com.likelion.be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration // 스프링 설정 클래스
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer 구현 클래스

    @Override // 메서드 오버라이드 어노테이션
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) { // HTTP 메시지 변환기 구성 메서드
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8)); // UTF-8 문자열 메시지 변환기 추가

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(); // JSON 메시지 변환기 생성
        jsonConverter.setDefaultCharset(StandardCharsets.UTF_8); // UTF-8 기본 문자셋 설정
        converters.add(jsonConverter); // JSON 메시지 변환기 추가
    }
}