package com.fastcampus.projectboardadmin.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        Long parentCommentId,
        String content,
        LocalDateTime createDate,
        String createUser,
        LocalDateTime updateDate,
        String updateUser
) {
    public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, Long parentCommentId, String content, LocalDateTime createDate, String createUser, LocalDateTime updateDate, String updateUser) {
        return new ArticleCommentDto(id, articleId, userAccountDto, parentCommentId, content, createDate, createUser, updateDate, updateUser);
    }


}
