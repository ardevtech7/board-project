package com.ardevtech.board.controller;

import com.ardevtech.board.domain.constant.FormStatus;
import com.ardevtech.board.domain.constant.SearchType;
import com.ardevtech.board.dto.UserAccountDto;
import com.ardevtech.board.dto.request.ArticleRequest;
import com.ardevtech.board.dto.response.ArticleResponse;
import com.ardevtech.board.dto.response.ArticleWithCommentsResponse;
import com.ardevtech.board.service.ArticleService;
import com.ardevtech.board.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "articles/index";
    }

    @GetMapping("{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));

        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());
        map.addAttribute("totalCount", articleService.getArticleCount());
        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map)
    {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    private String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);
        return "articles/form";
    }

    @PostMapping("/form")
    private String postNewArticle(ArticleRequest articleRequest) {
        // TODO : 인증 정보 추가 예정
        articleService.saveArticle(articleRequest.toDto(UserAccountDto.of(
                "ardev", "ardev1234", "asdev@mail.com", "Ardev", "memo",
                null, null, null, null
        )));

        return "redirect:/articles";
    }

    @GetMapping("/{articleId}/form")
    private String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

    @PostMapping("/{articleId}/form")
    public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest) {
        // TODO : 인증 정보 추가 예정
        articleService.updateArticle(articleId, articleRequest.toDto(UserAccountDto.of(
                "ardev", "ardev1234", "asdev@mail.com", "Ardev", "memo")));

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{articleId}/delete")
    private String deleteArticle(@PathVariable Long articleId) {
        // TODO : 인증 정보 추가 예정
        articleService.deleteArticle(articleId);

        return "redirect:/articles";
    }
}
