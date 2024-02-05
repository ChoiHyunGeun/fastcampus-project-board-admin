package com.fastcampus.projectboardadmin.domain;

import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import com.fastcampus.projectboardadmin.domain.converter.RoleTypesConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) //부모 클래스에 있는 필드들도 toString의 대상에 집어 넣겠다는 의미
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createDate"),
        @Index(columnList = "createUser")
})
@Entity
public class UserAccount extends AuditingFields {
    @Id
    @Column(length = 50)
    private String userId;

    @Setter
    @Column(nullable = false)
    private String userPassword;


    /**
     * 권한 테이블을 따로 만들어서 맵핑하는 방법도 있겠지만
     * 이번 어드민 강의에선 다른 방법으로 접근
     * 컬렉션에 "[1,2,3]"이렇게 값이 들어있다고 했을 때, 이 값을 한번에 DB에 넣어주고
     * 딜리미터를 `,`라고 약속을 해놓으면 컬렉션이 문자열로 한번에 저장이 되어도 값을 꺼낼 때는
     * 컬렉션으로 치환해서 컬렉션으로 세팅해주는 방식으로 구현함.
     * 우리는 이걸 JPA 구현체에 이것이 필드가 맞고, DB에 넘길 때, 통으로 합쳐서 문자열로 넘길 것이라는 걸 알려줄 필요가 있다.
     * 이런 일을 하는게 바로 `converter`임. jpa도 나름의 컨버터가 있음.
     * 그것을 구현한게 `RoleTypesConverter`
     */
    @Convert(converter = RoleTypesConverter.class)
    @Column(nullable = false)
    private Set<RoleType> roleTypes = new LinkedHashSet<>();


    @Setter
    @Column(length = 100)
    private String email;

    @Setter
    @Column(length = 100)
    private String nickname;

    @Setter
    private String memo;


    protected UserAccount() {}

    private UserAccount(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, String createUser) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.roleTypes = roleTypes;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
        this.createUser = createUser;
        this.updateUser = createUser;
    }

    /**
     * 이미 인증된 상태 > 기존 회원 > DB에 회원 정보가 있는 경우
     * 해당 of 메소드를 사용
     * @param userId
     * @param userPassword
     * @param email
     * @param nickname
     * @param memo
     * @return
     */
    public static UserAccount of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo) {
        return UserAccount.of(userId, userPassword, roleTypes, email, nickname, memo, null);
    }

    /**
     * 생성자 정보가 없는 경우 > ex) 회원가입하는 경우
     * 해당 of 메소드를 사용한다.
     * @param userId
     * @param userPassword
     * @param email
     * @param nickname
     * @param memo
     * @param createUser
     * @return
     */
    public static UserAccount of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, String createUser) {
        return new UserAccount(userId, userPassword, roleTypes, email, nickname, memo, createUser);
    }

    public void deleteRoleType(RoleType roleType) {
        this.getRoleTypes().remove(roleType);
    }

    public void addRoleType(RoleType roleType) {
        this.getRoleTypes().add(roleType);
    }

    public void addRoleTypes(Collection<RoleType> roleTypes) {
        this.getRoleTypes().addAll(roleTypes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount that)) return false;
        return userId != null && userId.equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

}
