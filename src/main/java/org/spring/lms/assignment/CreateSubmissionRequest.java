package org.spring.lms.assignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSubmissionRequest(@NotBlank @Size(max = 1000) String submissionUrl) { }
