package com.fastcampus.projectboardadmin.dto.response;

import com.fastcampus.projectboardadmin.dto.UserAccountDto;

import java.time.LocalDateTime;

public record UserAccountResponse(
        String userId,
        String email,
        String nickname,
        String memo,
        String createUser,
        LocalDateTime createDate
) {
    public static UserAccountResponse of(String userId, String email, String nickname, String memo, String createUser, LocalDateTime createDate) {
        return new UserAccountResponse(userId, email, nickname, memo, createUser, createDate);
    }

    public static UserAccountResponse from(UserAccountDto dto) {
        return UserAccountResponse.of(
          dto.userId(),
          dto.email(),
          dto.nickname(),
          dto.memo(),
          dto.createUser(),
          dto.createDate()
        );
    }
}
