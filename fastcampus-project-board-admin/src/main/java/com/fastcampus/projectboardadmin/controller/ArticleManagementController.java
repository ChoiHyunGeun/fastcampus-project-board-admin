package com.fastcampus.projectboardadmin.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/management/articles")
@Controller
public class ArticleManagementController {

    @GetMapping
    public String articles(
            @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap model,
            HttpServletRequest httpServletRequest
            ) {
        model.addAttribute("request",httpServletRequest);
        return "management/articles";
    }
}