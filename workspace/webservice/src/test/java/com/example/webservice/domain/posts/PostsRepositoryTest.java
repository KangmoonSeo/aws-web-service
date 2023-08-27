package com.example.webservice.domain.posts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository; // test: H2 database 자동 사용

    @AfterEach
    public void afterEach() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("AUTHOR")
                .build());

        // when
        List<Posts> posts = postsRepository.findAll();

        // then
        Posts post = posts.get(posts.size() - 1);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 8, 27, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("TITLE")
                .content("CONTENT")
                .author("AUTHOR")
                .build());
        // when
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(postsList.size() - 1);

        // then
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getLastModifiedDate()).isAfter(now);

    }
}