package org.spring.lms.assignment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "section", ignore = true)
    Assignment toEntity(CreateAssignmentRequest request);

    @Mapping(target = "sectionId", source = "section.id")
    @Mapping(target = "sectionTitle", source = "section.title")
    @Mapping(target = "taId", source = "section.teachingAssistant.id")
    @Mapping(target = "taName", source = "section.teachingAssistant.name")
    AssignmentResponse toResponse(Assignment assignment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "feedback", ignore = true)
    Submission toEntity(CreateSubmissionRequest request);

    @Mapping(target = "assignmentId", source = "assignment.id")
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "assignedTaId", source = "assignment.section.teachingAssistant.id")
    @Mapping(target = "assignedTaName", source = "assignment.section.teachingAssistant.name")
    SubmissionResponse toResponse(Submission submission);

}
