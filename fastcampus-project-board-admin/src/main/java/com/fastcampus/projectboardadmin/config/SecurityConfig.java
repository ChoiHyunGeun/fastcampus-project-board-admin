package com.fastcampus.projectboardadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private HandlerMappingIntrospector handlerMappingIntrospector;

    /**
     * 스프링 시큐리티의 관리 하에 두고 인증과 권한 체크를 하는 부분
     * @param http
     * @return
     * @throws Exception
     */
    @Bean //TODO : 최초로 pc 켜고, 서버를 실행 시키고, 로그인을 하면 error 화면이 나옴. 딱 이 조건일 때만 최초로 발생함. 확인하고 수정하기
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).formLogin(withDefaults()) //아무 일도 안하는 기본 값으로 동작하길 원한다면 withDefaults()를 사용
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(withDefaults())
                .build();
    }
}
