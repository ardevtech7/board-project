package com.ardevtech.board.repository.querydsl;

import com.ardevtech.board.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {
    /**
     * @deprecated 해시태그 도메인을 새로 만들었으니 이 코드는 필요없음
     * @see HashtagRepositoryCustom#findAllHashtagNames()
     */
    @Deprecated
    List<String> findAllDistinctHashtags();
    Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable);
}
