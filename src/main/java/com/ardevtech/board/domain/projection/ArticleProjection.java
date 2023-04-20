package com.ardevtech.board.domain.projection;

import com.ardevtech.board.domain.Article;
import com.ardevtech.board.domain.UserAccount;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "withUserAccount", types = Article.class) // 데이터를 표현하는 방법을 수동으로 설정
public interface ArticleProjection {
    // Article.class 를 참고해서 노출하고 싶은 메소드 설정
    Long getId();
    UserAccount getUserAccount();
    String getTitle();
    String getContent();
    LocalDateTime getCreatedAt();
    String getCreatedBy();
    LocalDateTime getModifiedAt();
    String getModifiedBy();
}
