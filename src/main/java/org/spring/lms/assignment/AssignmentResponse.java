package org.spring.lms.assignment;

import java.time.Instant;

public record AssignmentResponse(Long id, Long sectionId, String sectionTitle, String title,
                                 String instructions, Instant dueAt, Long taId, String taName) { }
