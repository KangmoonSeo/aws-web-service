package com.example.webservice.web;

import com.example.webservice.domain.posts.Posts;
import com.example.webservice.domain.posts.PostsRepository;
import com.example.webservice.dto.PostsRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    // new for spring security test
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @BeforeEach
    public void beforeEach() {
        // setup
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void afterEach() {
        postsRepository.deleteAll(); // teardown all
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_등록된다() throws Exception {
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
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        // then

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(all.size() - 1).getTitle()).isEqualTo(title);
        assertThat(all.get(all.size() - 1).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 수정된다() throws Exception {
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
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(all.size() - 1).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(all.size() - 1).getContent()).isEqualTo(expectedContent);
    }
}