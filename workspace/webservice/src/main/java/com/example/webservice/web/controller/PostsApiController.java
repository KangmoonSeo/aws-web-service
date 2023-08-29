package com.example.webservice.web.controller;

import com.example.webservice.service.PostsService;
import com.example.webservice.web.dto.PostsResponseDto;
import com.example.webservice.web.dto.PostsRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping
    public List<PostsResponseDto> findAll() {
        return postsService.findAll();
    }

    @PostMapping
    public Long save(@RequestBody PostsRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    // == mock data for dev environment == //
    @PostConstruct
    public void init() {
        if (!postsService.findAll().isEmpty()) return;

        postsService.save(
                PostsRequestDto.builder()
                        .title("테스트 게시글입니다. ")
                        .content("lorem ipsum")
                        .author("관리자")
                        .build());
        postsService.save(
                PostsRequestDto.builder()
                        .title("테스트 게시글입니다. (2)")
                        .content("text message for test")
                        .author("관리자")
                        .build());
    }
}
