package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fastcampus.projectboardadmin.dto.response.ArticleResponse;
import com.fastcampus.projectboardadmin.service.ArticleManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/management/articles")
@Controller
public class ArticleManagementController {

    private final ArticleManagementService articleManagementService;

    @GetMapping
    public String articles(
            ModelMap model,
            HttpServletRequest httpServletRequest
    ) {
        model.addAttribute("request",httpServletRequest);
        model.addAttribute("articles",
                articleManagementService.getArticles().stream()
                        .map(ArticleResponse::withoutContent) // 게시글 상세가 아니기 때문에 내용이 보일 필요는 없음
                        .toList()
        );

        return "management/articles";
    }

    @ResponseBody
    @GetMapping("/{articleId}")
    public ArticleResponse article(
            @PathVariable Long articleId
    ) {
        ArticleDto article = articleManagementService.getArticle(articleId);

        return ArticleResponse.withContent(article);
    }

    @PostMapping("/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        articleManagementService.deleteArticle(articleId);

        return "redirect:/management/articles";
    }
}
