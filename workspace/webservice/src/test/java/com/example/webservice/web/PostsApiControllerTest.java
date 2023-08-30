package com.example.webservice.web;

import com.example.webservice.domain.posts.Posts;
import com.example.webservice.domain.posts.PostsRepository;
import com.example.webservice.dto.PostsRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void afterEach() {
        postsRepository.deleteAll(); // teardown all
    }

    @Test
    public void Posts_등록된다() {
        // given
        String title = "TITLE";
        String content = "CONTENT";
        PostsRequestDto requestDto = PostsRequestDto.builder()
                .title(title)
                .content(content)
                .author("AUTHOR")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";
        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(all.size() - 1).getTitle()).isEqualTo(title);
        assertThat(all.get(all.size() - 1).getContent()).isEqualTo(content);
    }

    @Test
    public void 수정된다() {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("TITLE")
                .content("CONTENT")
                .author("AUTHOR")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title 2";
        String expectedContent = "content 2";

        PostsRequestDto requestDto = PostsRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        HttpEntity<PostsRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(all.size() - 1).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(all.size() - 1).getContent()).isEqualTo(expectedContent);
    }
}