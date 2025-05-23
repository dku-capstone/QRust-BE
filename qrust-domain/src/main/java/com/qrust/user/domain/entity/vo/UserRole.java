package com.qrust.user.domain.entity.vo;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public enum UserRole {
    USER,
    BUSINESS,
    ADMIN;

    private static final Map<UserRole, Set<UserRole>> hierarchy = new EnumMap<>(UserRole.class);

    static {
        hierarchy.put(USER, Set.of(USER));
        hierarchy.put(BUSINESS, Set.of(USER, BUSINESS));
        hierarchy.put(ADMIN, Set.of(USER, BUSINESS, ADMIN));
    }

    public static boolean isRoleIncluded(UserRole having, UserRole required) {
        return hierarchy.getOrDefault(having, Collections.emptySet()).contains(required);
    }
}
