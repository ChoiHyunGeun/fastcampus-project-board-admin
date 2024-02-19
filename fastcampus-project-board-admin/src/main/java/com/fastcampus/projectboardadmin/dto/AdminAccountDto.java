package com.fastcampus.projectboardadmin.dto;

import com.fastcampus.projectboardadmin.domain.AdminAccount;
import com.fastcampus.projectboardadmin.domain.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

public record AdminAccountDto(
        String userId,
        String userPassword,
        Set<RoleType> roleTypes,
        String email,
        String nickname,
        String memo,
        LocalDateTime createDate,
        String createUser,
        LocalDateTime updateDate,
        String updateUser
) {

    public static AdminAccountDto of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo) {
        return AdminAccountDto.of(userId, userPassword, roleTypes, email, nickname, memo, null, null, null, null);
    }

    public static AdminAccountDto of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, LocalDateTime createDate, String createUser, LocalDateTime updateDate, String updateUser) {
        return new AdminAccountDto(userId, userPassword, roleTypes, email, nickname, memo, createDate, createUser, updateDate, updateUser);
    }

    public static AdminAccountDto from(AdminAccount entity) {
        return new AdminAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getRoleTypes(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreateDate(),
                entity.getCreateUser(),
                entity.getUpdateDate(),
                entity.getUpdateUser()
        );
    }

    public AdminAccount toEntity() {
        return AdminAccount.of(
                userId,
                userPassword,
                roleTypes,
                email,
                nickname,
                memo
        );
    }

}