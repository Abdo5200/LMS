package org.spring.lms.course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "instructors", ignore = true)
    Course toEntity(CreateCourseRequest request);

    @Mapping(target = "instructors", source = "instructors")
    CourseResponse toResponse(Course course);

    @Mapping(target = "id", source = "instructor.id")
    @Mapping(target = "name", source = "instructor.name")
    InstructorSummary toInstructorSummary(CourseInstructor instructor);
}
