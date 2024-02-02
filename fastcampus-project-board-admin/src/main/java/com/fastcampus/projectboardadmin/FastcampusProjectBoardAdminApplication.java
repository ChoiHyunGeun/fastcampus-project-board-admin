package com.fastcampus.projectboardadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @ConfigurationPropertiesScan
 * 이 어노테이션은 루트 패스를 기준으로 모든 설정 클래스로부터 configuration property가 있는지 스케닝한다.
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class FastcampusProjectBoardAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastcampusProjectBoardAdminApplication.class, args);
    }

}
