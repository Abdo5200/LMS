package org.spring.lms.course;

public record LectureResponse(Long id, Long courseId, String title, String presentationUrl,
                              Long instructorId, String instructorName, int position) { }
