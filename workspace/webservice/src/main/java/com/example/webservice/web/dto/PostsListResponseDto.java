package com.example.webservice.web.dto;

import com.example.webservice.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class PostsListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime lastModifiedDate;


    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.lastModifiedDate = entity.getLastModifiedDate();
    }
}
