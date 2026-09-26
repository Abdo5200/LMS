package org.spring.lms.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateCourseRequest(
        @NotBlank @Size(max = 160) String title,
        @NotBlank @Size(max = 4000) String description,
        @NotNull @Size(min = 1, max = 2) List<@NotNull Long> instructorIds) { }
