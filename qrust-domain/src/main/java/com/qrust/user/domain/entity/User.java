package com.qrust.user.domain.entity;

import com.qrust.auth.dto.SignUpRequest;
import com.qrust.common.infrastructure.jpa.shared.BaseEntity;
import com.qrust.user.domain.entity.vo.LoginType;
import com.qrust.user.domain.entity.vo.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.type.YesNoConverter;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "is_withdrawal", nullable = false)
    private boolean isWithdraw = false;

    @Builder
    public User(String email, String userName, UserRole userRole, LoginType loginType, boolean isWithdraw) {
        this.email = email;
        this.userName = userName;
        this.userRole = userRole;
        this.loginType = loginType;
        this.isWithdraw = isWithdraw;
    }

    public static User of(SignUpRequest request) {
        return User.builder()
                .email(request.email())
                .userName(request.userName())
                .userRole(UserRole.USER)
                .loginType(LoginType.EMAIL)
                .isWithdraw(false)
                .build();
    }
}
