package org.spring.lms.course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    Lecture toEntity(CreateLectureRequest request);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "instructorId", source = "instructor.id")
    @Mapping(target = "instructorName", source = "instructor.name")
    LectureResponse toResponse(Lecture lecture);
}
