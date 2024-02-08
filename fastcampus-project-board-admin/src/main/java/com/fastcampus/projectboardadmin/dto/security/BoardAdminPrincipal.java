package com.fastcampus.projectboardadmin.dto.security;

import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import com.fastcampus.projectboardadmin.dto.AdminAccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardAdminPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String memo,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {
    /**
     * UserDetails 인터페이스는 Spring security에서 제공하는 기능이며 사용자의 정보를 담는 핵심 인터페이스임
     * 이 인터페이스를 통해 Spring security는 사용자의 인증 정보를 관리하고 접근을 제어함
     */
    public static BoardAdminPrincipal of(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo) {
        return BoardAdminPrincipal.of(username, password, roleTypes, email, nickname, memo, Map.of());
    }

    /**
     * 카카오 인증 로직을 탔을 경우
     */
    public static BoardAdminPrincipal of(String username, String password, Set<RoleType> roleTypes, String email, String nickname, String memo, Map<String, Object> oAuth2Attributes) {
        return new BoardAdminPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getRoleName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                nickname,
                memo,
                oAuth2Attributes
        );
    }

    public static BoardAdminPrincipal from(AdminAccountDto dto){
        return BoardAdminPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.roleTypes(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public AdminAccountDto toDto(){
        return AdminAccountDto.of(
                username,
                password,
                authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(RoleType::valueOf)
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                nickname,
                memo
        );
    }

    /**
     * 사용자가 가진 권한을 Collection<? extens GrantedAuthority> 형태로 반환한다.
     * 권한은 사용자가 수행할 수 있는 작업(ROLE_USER, ROLE_ADMIN)을 나타낸다
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 사용자의 비밀번호 반환
     * 비밀번호는 암호화된 형태로 저장되고 관리됨
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 사용자의 이름 또는 아이디를 반환함. 사용자를 구별하는 고유한 값
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 계정의 만료 여부를 반환
     * 만료되지 않은 경우 true를 반환 함
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠겨있는지 여부를 반환 함.
     * 잠겨있지 않은 경우 true를 반환 함
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 사용자의 인증 정보(비밀번호 등)가 만료되었는지 여부를 반환 함.
     * 만료되지 않았으면 true를 반환 함.
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 계정이 활성화되어 있는지 여부를 반환 함.
     * 활성화된 경우 true를 반환
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }



    /**
     * OAuth2User 인터페이스의 구현체
     * @return
     */
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    /**
     * OAuth2User 인터페이스의 구현체
     * @return
     */
    @Override
    public String getName() {
        return username;
    }
}
