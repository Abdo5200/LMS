package org.spring.lms.enrollment;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enroll(@RequestBody EnrollmentRequest enrollmentRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.enroll(enrollmentRequest));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> listForStudent(@RequestParam @NotNull Long studentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getStudentEnrollments(studentId));
    }
}
