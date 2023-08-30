package com.example.webservice.web;

import com.example.webservice.dto.HelloRequestDto;
import com.example.webservice.dto.HelloResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void hello가_리턴된다() {
        // given
        String hello = "hello";

        // when
        String url = "http://localhost:" + port + "/hello";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(hello);

    }

    @Test
    public void helloDto가_리턴된다() {
        // given
        String name = "hello";
        Integer amount = 1000;
        String url = "http://localhost:" + port + "/hello/dto";

        // when
        ResponseEntity<HelloResponseDto> responseEntity = restTemplate.postForEntity(
                url,
                HelloRequestDto.builder()
                        .name(name)
                        .amount(amount)
                        .build(),
                HelloResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
        assertThat(responseEntity.getBody().getAmount()).isEqualTo(amount * 2);
    }
}