package com.fastcampus.projectboardadmin.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleDto(
        Long id,
        UserAccountDto userAccount,
        String title,
        String content,
        Set<String> hashtags,
        LocalDateTime createDate,
        String createUser,
        LocalDateTime updateDate,
        String updateUser
) {
    public static ArticleDto of(Long id, UserAccountDto userAccount, String title, String content, Set<String> hashtags, LocalDateTime createDate, String createUser, LocalDateTime updateDate, String updateUser) {
        return new ArticleDto(id, userAccount, title, content, hashtags, createDate, createUser, updateDate, updateUser);
    }

    
}
