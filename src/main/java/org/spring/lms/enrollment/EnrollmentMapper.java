package org.spring.lms.enrollment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")
    EnrollmentResponse toResponse(Enrollment enrollment);
}
