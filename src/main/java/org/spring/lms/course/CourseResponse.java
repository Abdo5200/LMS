package org.spring.lms.course;

import java.time.Instant;
import java.util.List;

public record CourseResponse(Long id, String title, String description, boolean published,
                             List<InstructorSummary> instructors, Instant createdAt) { }
