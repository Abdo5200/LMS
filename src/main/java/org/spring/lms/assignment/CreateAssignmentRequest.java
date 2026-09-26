package org.spring.lms.assignment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public record CreateAssignmentRequest(@NotBlank @Size(max = 160) String title,
                                      @NotBlank @Size(max = 4000) String instructions,
                                      Instant dueAt) { }
