package com.example.webservice.controller;

import com.example.webservice.dto.PostsListResponseDto;
import com.example.webservice.dto.PostsResponseDto;
import com.example.webservice.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model) {
        List<PostsListResponseDto> lists = postsService.findAllDesc();
        model.addAttribute("lists", lists);
        return "index";
    }

    @GetMapping("/posts/new")
    public String createPosts() {
        return "posts/createPostsForm";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPosts(@PathVariable Long id, Model model) {

        PostsResponseDto posts = postsService.findById(id);
        model.addAttribute("posts", posts);
        return "posts/editPostsForm";
    }


}
