package com.example.webservice.service;

import com.example.webservice.domain.BaseTimeEntity;
import com.example.webservice.domain.posts.Posts;
import com.example.webservice.domain.posts.PostsRepository;
import com.example.webservice.web.dto.PostsResponseDto;
import com.example.webservice.web.dto.PostsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
@Transactional
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity())
                .getId();
    }

    public Long update(Long id, PostsRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(noMatchedException(id));
        posts.update(requestDto.toEntity());
        return posts.getId();
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(noMatchedException(id));
        return new PostsResponseDto(entity);
    }
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAll() {

        List<Posts> posts = postsRepository.findAll();
        List<PostsResponseDto> ret = new ArrayList<>();

        posts.forEach(p -> ret.add(new PostsResponseDto(p)));
        return ret;
    }


    // == exception == //
    private static Supplier<IllegalArgumentException> noMatchedException(Long id) {
        return () ->
                new IllegalArgumentException("해당 사용자가 없습니다. id=" + id);
    }
}
