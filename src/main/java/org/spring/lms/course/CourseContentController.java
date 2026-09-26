package org.spring.lms.course;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}")
public class CourseContentController {
    private final CourseContentService service;

    public CourseContentController(CourseContentService service) {
        this.service = service;
    }

    @PostMapping("/sections")
    public ResponseEntity<SectionResponse> addSection(@PathVariable Long courseId, @Valid @RequestBody CreateSectionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addSection(courseId, request));
    }

    @GetMapping("/sections")
    public ResponseEntity<List<SectionResponse>> sections(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.sections(courseId));
    }

    @PostMapping("/lectures")
    public ResponseEntity<LectureResponse> addLecture(@PathVariable Long courseId, @Valid @RequestBody CreateLectureRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addLecture(courseId, request));
    }

    @GetMapping("/lectures")
    public ResponseEntity<List<LectureResponse>> lectures(@PathVariable Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.lectures(courseId));
    }
}
