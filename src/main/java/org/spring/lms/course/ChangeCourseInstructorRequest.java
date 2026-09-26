package org.spring.lms.course;

import jakarta.validation.constraints.NotNull;

public record ChangeCourseInstructorRequest(@NotNull Long oldInstructorId,
                                            @NotNull Long newInstructorId) { }
