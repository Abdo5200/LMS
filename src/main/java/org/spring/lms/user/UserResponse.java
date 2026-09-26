package org.spring.lms.user;

import java.time.Instant;

public record UserResponse(Long id, String name, String email, UserRole role, Instant createdAt) { }
