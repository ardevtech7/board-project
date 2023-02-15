package com.ardevtech.board.service;

import com.ardevtech.board.dto.ArticleCommentDto;
import com.ardevtech.board.repository.ArticleCommentRepository;
import com.ardevtech.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {

    }

    public void updateArticleComment(ArticleCommentDto dto) {

    }

    public void deleteArticleComment(Long articleCommentId) {

    }
}
