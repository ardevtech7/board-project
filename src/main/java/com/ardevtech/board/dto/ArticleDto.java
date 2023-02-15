package com.ardevtech.board.dto;

import java.time.LocalDateTime;

public record ArticleDto (
    LocalDateTime createdAt,
    String createdBy,
    String title,
    String contend,
    String hashtag
) {
    public static ArticleDto of (LocalDateTime createdAt,
                                 String createdBy,
                                 String title,
                                 String contend,
                                 String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, contend, hashtag);
    }
}
