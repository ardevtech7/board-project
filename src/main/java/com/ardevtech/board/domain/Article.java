package com.ardevtech.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Setter
    private String hashtag;

    // Article 에 연동된 Comment 는 중복을 허용하지 않는다.
    @ToString.Exclude // 순환 참조가 생길 수 있으니 적용
    @OrderBy("id") // 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // article 테이블로부터 온 거라는 것을 명시
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // 모든 JPA Entity 는 기본 생성자 필수. private X
    protected Article() {

    }

    // metadata(id, createdAt, createdBy, modifiedAt, modifiedBy) 는 자동으로 들어가야하니 일단 제외
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // private 로 생성자 막고, 팩토리 메서드로 생성자를 제공할 수 있도록 적용
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // collection 에서 사용 시 list 에서 넣거나 중복, 정렬 등을 하면서 비교, 동등성 비교할 때 필요
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Article article))
            return false;
        // db 에 insert 전 엔티티는 아직 널이기 때문에 체크하는 코드로 변경
//        return id.equals(article.id)
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
