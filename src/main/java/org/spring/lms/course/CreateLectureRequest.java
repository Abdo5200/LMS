package org.spring.lms.course;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateLectureRequest(@NotBlank @Size(max = 160) String title,
                                   @NotBlank @Size(max = 1000) String presentationUrl,
                                   @NotNull Long instructorId,
                                   @NotNull @Min(1) Integer position) { }
