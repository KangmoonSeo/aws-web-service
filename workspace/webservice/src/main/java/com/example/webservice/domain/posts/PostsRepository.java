package com.example.webservice.domain.posts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Entity 클래스와 기본 Entity Repository 는 함께 위치해야 함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {


    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
