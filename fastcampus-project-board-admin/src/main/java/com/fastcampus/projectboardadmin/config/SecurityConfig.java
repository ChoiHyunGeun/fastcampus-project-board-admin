package com.fastcampus.projectboardadmin.config;

import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import com.fastcampus.projectboardadmin.dto.security.BoardAdminPrincipal;
import com.fastcampus.projectboardadmin.dto.security.KakaoOAuth2Response;
import com.fastcampus.projectboardadmin.service.AdminAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Set;
import java.util.UUID;

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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] rolesAboveManager = {RoleType.MANAGER.name(), RoleType.ADMIN.name(), RoleType.DEVELOPER.name()};

        MvcRequestMatcher postRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/**");
        postRequestMatcher.setMethod(HttpMethod.POST);

        MvcRequestMatcher deleteRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/**");
        postRequestMatcher.setMethod(HttpMethod.DELETE);

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(postRequestMatcher).hasAnyRole(rolesAboveManager)
                        .requestMatchers(deleteRequestMatcher).hasAnyRole(rolesAboveManager)
                        .anyRequest().authenticated() //https://spacee-for-me.tistory.com/50 에 정리
                )
                .formLogin(withDefaults()) //아무 일도 안하는 기본 값으로 동작하길 원한다면 withDefaults()를 사용
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetatilsService(AdminAccountService adminAccountService) {
        return username -> adminAccountService
                .searchUser(username)
                .map(BoardAdminPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username : " + username));
    }

    /**
     * <p>
     * OAuth 2.0 기술을 이용한 인증 정보를 처리한다.
     * 카카오 인증 방식을 선택.
     *
     * @param adminAccountService  게시판 서비스의 사용자 계정을 다루는 서비스 로직
     * @param passwordEncoder 패스워드 암호화 도구
     * @return {@link OAuth2UserService} OAuth2 인증 사용자 정보를 읽어들이고 처리하는 서비스 인스턴스 반환
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            AdminAccountService adminAccountService,
            PasswordEncoder passwordEncoder
    ) {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            KakaoOAuth2Response kakaoResponse = KakaoOAuth2Response.from(oAuth2User.getAttributes());
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            String providerId = String.valueOf(kakaoResponse.id());
            String username = registrationId + "_" + providerId;
            String dummyPassword = passwordEncoder.encode("{bcrypt}" + UUID.randomUUID());
            Set<RoleType> roleTypes = Set.of(RoleType.MANAGER);

            /**
             * TODO 관리자 회원가입은 하나의 절차가 더 있었으면 좋겠음.
             * 뭐 관리자 인증 번호라던지 회사에서 부여받은 코드를 입력하는 절차가 더 필요할 것 같다.
             */

            return adminAccountService.searchUser(username)
                    .map(BoardAdminPrincipal::from)
                    .orElseGet(() ->
                            BoardAdminPrincipal.from(
                                    adminAccountService.saveUser(
                                            username,
                                            dummyPassword,
                                            roleTypes,
                                            kakaoResponse.email(),
                                            kakaoResponse.nickname(),
                                            null
                                    )
                            )
                    );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
