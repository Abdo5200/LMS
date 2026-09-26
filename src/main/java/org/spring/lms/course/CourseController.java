package org.spring.lms.course;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> browse() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> get(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getCourse(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CreateCourseRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}/instructors")
    public ResponseEntity<CourseResponse> changeInstructor(@PathVariable Long id, @Valid @RequestBody ChangeCourseInstructorRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.changeInstructor(id, request));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<CourseResponse> publish(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.publish(id));
    }
}
