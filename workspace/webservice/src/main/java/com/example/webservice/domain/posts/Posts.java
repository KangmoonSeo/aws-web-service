package com.example.webservice.domain.posts;

import com.example.webservice.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(Posts target) {
        if (target.getTitle() != null) this.title = target.getTitle();
        if (target.getContent() != null) this.content = target.getContent();
        if (target.getAuthor() != null) this.author = target.getAuthor();
    }
}
