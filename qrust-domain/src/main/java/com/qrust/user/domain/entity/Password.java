package com.qrust.user.domain.entity;

import com.qrust.common.infrastructure.jpa.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_password")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "password", nullable = false)
    private String pwd;

    @Builder
    public Password(Long userId, String salt, String pwd) {
        this.userId = userId;
        this.salt = salt;
        this.pwd = pwd;
    }

    public static Password of (Long userId, String salt, String pwd){
        return Password.builder()
                .userId(userId)
                .salt(salt)
                .pwd(pwd)
                .build();
    }
}
