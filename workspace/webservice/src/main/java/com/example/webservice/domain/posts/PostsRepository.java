package com.example.webservice.domain.posts;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Entity 클래스와 기본 Entity Repository 는 함께 위치해야 함
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
