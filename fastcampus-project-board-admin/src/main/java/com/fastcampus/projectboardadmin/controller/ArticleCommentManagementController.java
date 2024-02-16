package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.ArticleCommentDto;
import com.fastcampus.projectboardadmin.dto.response.ArticleClientResponse;
import com.fastcampus.projectboardadmin.dto.response.ArticleCommentResponse;
import com.fastcampus.projectboardadmin.service.ArticleCommentManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/management/article-comments")
@Controller
public class ArticleCommentManagementController {
    private final ArticleCommentManagementService articleCommentManagementService;

    /**
     * 댓글 리스트 전체 조회
     */
    @GetMapping
    public String articleComments(
            ModelMap model,
            HttpServletRequest httpServletRequest
    ) {
        List<ArticleCommentResponse> comments = articleCommentManagementService.getArticleComments().stream()
                        .map(ArticleCommentResponse::of).toList();

        model.addAttribute("request",httpServletRequest);
        model.addAttribute("comments", comments);

        return "management/article-comments";
    }

    /**
     * 댓글 단건 조회
     */
    @ResponseBody
    @GetMapping("/{articleCommentId}")
    public ArticleCommentResponse articleComment(
            @PathVariable Long articleCommentId
    ){
        return ArticleCommentResponse.of(articleCommentManagementService.getArticleComment(articleCommentId));
    }

    /**
     * 댓글 삭제
     */
    @PostMapping("/{articleCommentId}")
    public String deleteArticleComment(@PathVariable Long articleCommentId) {
        articleCommentManagementService.deleteArticleComment(articleCommentId);

        return "redirect:/management/article-comments";
    }
}
