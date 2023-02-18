package com.ardevtech.board.dto.response;

import com.ardevtech.board.dto.ArticleCommentDto;
import com.ardevtech.board.dto.ArticleWithCommentsDto;
import com.ardevtech.board.dto.HashtagDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
        Long id,
        String title,
        String content,
        Set<String> hashtags,
        LocalDateTime createdAt,
        String email,
        String nickname,
        String userId,
        Set<ArticleCommentResponse> articleCommentsResponse
) {
    public static ArticleWithCommentsResponse of(
            Long id,
            String title,
            String content,
            Set<String> hashtags,
            LocalDateTime createdAt,
            String email,
            String nickname,
            String userId,
            Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentsResponse(
                id,
                title,
                content,
                hashtags,
                createdAt,
                email,
                nickname,
                userId,
                articleCommentResponses);
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleWithCommentsResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtagDtos().stream()
                                .map(HashtagDto::hashtagName)
                                        .collect(Collectors.toUnmodifiableSet()),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname,
                dto.userAccountDto().userId(),
                organizeChildComments(dto.articleCommentDtos())
        );
    }

    // 댓글과 대댓글이 구분되어 있지 않은 테이블 순환
    // 대댓글이면 부모 아이디 있고, 댓글이면 부모 아이디 없으니 루트 아이디 확보
    private static Set<ArticleCommentResponse> organizeChildComments(Set<ArticleCommentDto> dtos) {
        Map<Long, ArticleCommentResponse> map = dtos.stream()
                .map(ArticleCommentResponse::from)
                .collect(Collectors.toMap(ArticleCommentResponse::id, Function.identity()));

        map.values().stream()
                .filter(ArticleCommentResponse::hasParentComment) // 부모 댓글이 있으면 자식 댓글
                .forEach(comment -> { // 자식 댓글만 필터링
                    // 부모 댓글 꺼내기
                    ArticleCommentResponse parentComment = map.get(comment.parentCommentId());
                    // 부모 댓글에서 자식 댓글 꺼내서 저장
                    parentComment.childComments().add(comment);
                });

        return map.values().stream()
                .filter(comment -> !comment.hasParentComment())
                .collect(Collectors.toCollection(() ->
                    new TreeSet<>(Comparator
                            .comparing(ArticleCommentResponse::createdAt)
                            .reversed()
                            .thenComparingLong(ArticleCommentResponse::id)
                    )));
    }
}
