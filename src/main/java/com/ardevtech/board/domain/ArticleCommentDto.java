package com.ardevtech.board.domain;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String contend
) {
    public static ArticleCommentDto of (
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy,
            String contend
    ) {
        return new ArticleCommentDto(createdAt, createdBy, modifiedAt, modifiedBy, contend);
    }

}
