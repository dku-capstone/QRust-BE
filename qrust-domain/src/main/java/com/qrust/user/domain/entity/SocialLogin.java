package com.qrust.user.domain.entity;

import com.qrust.infrastructure.jpa.shared.BaseEntity;
import com.qrust.user.domain.entity.vo.Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_social_login")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialLogin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_name", nullable = false)
    private Provider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;
}
