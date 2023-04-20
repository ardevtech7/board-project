package com.ardevtech.board.config;

import com.ardevtech.board.domain.Article;
import com.ardevtech.board.domain.ArticleComment;
import com.ardevtech.board.domain.Hashtag;
import com.ardevtech.board.domain.UserAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class DataRestConfig {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig((config, cors) ->
                // 아래 설정된 정보는 ID 정보 노출 -> API 에 공개한다는 것
                config
                        .exposeIdsFor(UserAccount.class)
                        .exposeIdsFor(Article.class)
                        .exposeIdsFor(ArticleComment.class)
                        .exposeIdsFor(Hashtag.class)
        );
    }
}
