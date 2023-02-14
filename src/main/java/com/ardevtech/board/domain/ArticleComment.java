package com.ardevtech.board.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article article; // 게시글 (ID)
    private String content;

    private LocalDateTime createAt;
    private String createdBy;
    private LocalDateTime modifyAt;
    private String modifiedBy;
}
