package com.fastcampus.projectboardadmin.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());

        return defaultTemplateResolver;
    }


    /**
     *  application.yaml 또는 .properties에 정의된 변수를 사용하기 위해서 선언
     *  클래스에 @ConfigurationProperties를 지정하게 되면 application.yaml 파일의 값을 읽어와서
     *  맴버변수에 자동으로 할당함
     *  application.yaml파일의 key값이 하이픈(-)이 포함된 경우 카멜표기법으로 변환된 key가 맴버변수와 연결됨
     * */
    @ConfigurationProperties("spring.thymeleaf3")
    public record Thymeleaf3Properties(boolean decoupledLogic) {

    }

}