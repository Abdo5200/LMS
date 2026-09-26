package org.spring.lms.assignment;

import java.time.Instant;

public record SubmissionResponse(Long id, Long assignmentId, Long studentId, String studentName,
                                 String submissionUrl, Instant submittedAt, Integer grade, String feedback,
                                 Long assignedTaId, String assignedTaName) { }
