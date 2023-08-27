package com.example.webservice.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // JPA Auditing
public abstract class BaseTimeEntity {
    @CreatedDate // Entity 생성 시점에 시간이 자동 저장됨
    private LocalDateTime createdDate;

    @LastModifiedDate // Entity 값을 변경할 때 시간이 자동으로 저장됨
    private LocalDateTime lastModifiedDate;
}
