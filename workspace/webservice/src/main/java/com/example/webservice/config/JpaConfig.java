package com.example.webservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // JPA Auditing 기능을 분리하여 테스트 케이스를 유연하게 만듦 (@WebMvcTest에 적용하지 않음)
public class JpaConfig {
}
