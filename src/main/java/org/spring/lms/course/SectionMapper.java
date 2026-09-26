package org.spring.lms.course;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "teachingAssistant", ignore = true)
    CourseSection toEntity(CreateSectionRequest request);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "teachingAssistantId", source = "teachingAssistant.id")
    @Mapping(target = "teachingAssistantName", source = "teachingAssistant.name")
    SectionResponse toResponse(CourseSection section);
}
