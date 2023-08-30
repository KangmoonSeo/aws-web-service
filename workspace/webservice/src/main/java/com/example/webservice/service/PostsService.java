package com.example.webservice.service;

import com.example.webservice.domain.posts.Posts;
import com.example.webservice.domain.posts.PostsRepository;
import com.example.webservice.dto.PostsListResponseDto;
import com.example.webservice.dto.PostsRequestDto;
import com.example.webservice.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(noMatchedException(id));

        postsRepository.delete(posts);
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

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc()
                .stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }


    // == exception == //
    private static Supplier<IllegalArgumentException> noMatchedException(Long id) {
        return () ->
                new IllegalArgumentException("해당 사용자가 없습니다. id=" + id);
    }
}
