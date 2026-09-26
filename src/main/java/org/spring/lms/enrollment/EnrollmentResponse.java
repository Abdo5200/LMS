package org.spring.lms.enrollment;

import java.time.Instant;

public record EnrollmentResponse(Long id, Long studentId, Long courseId, String courseTitle,
                                 EnrollmentStatus status, Instant enrolledAt) { }
