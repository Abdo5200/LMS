package org.spring.lms.course;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseContentController {
    private final CourseContentService service;

    public CourseContentController(CourseContentService service) {
        this.service = service;
    }

    @PostMapping("/api/courses/{courseId}/sections")
    public ResponseEntity<SectionResponse> addSection(@PathVariable Long courseId, @Valid @RequestBody CreateSectionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addSection(courseId, request));
    }

    @GetMapping("/api/courses/{courseId}/sections")
    public ResponseEntity<List<SectionResponse>> sections(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.sections(courseId));
    }

    @PostMapping("/api/courses/{courseId}/lectures")
    public ResponseEntity<LectureResponse> addLecture(@PathVariable Long courseId, @Valid @RequestBody CreateLectureRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addLecture(courseId, request));
    }

    @GetMapping("/api/courses/{courseId}/lectures")
    public ResponseEntity<List<LectureResponse>> lectures(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.lectures(courseId));
    }
}
