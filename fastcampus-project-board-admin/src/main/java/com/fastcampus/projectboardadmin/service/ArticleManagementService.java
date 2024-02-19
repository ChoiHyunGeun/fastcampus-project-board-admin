package com.fastcampus.projectboardadmin.service;

import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fastcampus.projectboardadmin.dto.properties.ProjectProperties;
import com.fastcampus.projectboardadmin.dto.response.ArticleClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleManagementService {

    private final RestTemplate restTemplate;
    private final ProjectProperties projectProperties;  //application.yaml에 설정한 property에 접근하기 위해 선언

    public List<ArticleDto> getArticles() {
        //게시판 서버에서 게시글 데이터를 가져온다. 외부 서버 데이터를 가져오는 것
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url() + "/api/articles")
                .queryParam("size", 10000) // TODO: 전체 게시글을 가져오기 위해 충분히 큰 사이즈를 전달하는 방식. 불완전하다.
                .build()
                .toUri();
        ArticleClientResponse response = restTemplate.getForObject(uri, ArticleClientResponse.class);

        return Optional.ofNullable(response).orElseGet(ArticleClientResponse::empty).articles();
    }

    public ArticleDto getArticle(Long articleId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url() + "/api/articles/" + articleId)
                .queryParam("projection", "withUserAccount")
                .build()
                .toUri();

        ArticleDto response = restTemplate.getForObject(uri, ArticleDto.class);

        return Optional.ofNullable(response)
                .orElseThrow(() ->new NoSuchElementException("게시글이 없습니다. - articleId : " + articleId));
    }

    public void deleteArticle(Long articleId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.board().url() + "/api/articles" + articleId)
                .build()
                .toUri();

        restTemplate.delete(uri);
    }
}
