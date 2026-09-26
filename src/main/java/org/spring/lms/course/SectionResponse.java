package org.spring.lms.course;

public record SectionResponse(Long id, Long courseId, String title, String sheetUrl,
                              Long teachingAssistantId, String teachingAssistantName, int position) { }
