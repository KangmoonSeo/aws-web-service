package com.example.webservice.config.oauth;

import com.example.webservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .headers().frameOptions().disable()

                .and()
                .authorizeRequests()// URL 별 권한 관리의 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 만 열람 가능
                .anyRequest().authenticated() // else URL 은 인증된 사용자들에게만 허용

                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 성공시 "/"로 이동

                .and()
                .oauth2Login() // 로그인 기능의 진입점
                .userInfoEndpoint()
                .userService(customOAuth2UserService); // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스 구현체
                // 소셜 서비스에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능들을 명시할 수 있음
    }
}
