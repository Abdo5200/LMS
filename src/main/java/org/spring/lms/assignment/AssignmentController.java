package org.spring.lms.assignment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class AssignmentController {
    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    // make an assignment for a section
    @PostMapping("/api/sections/{sectionId}/assignment")
    public ResponseEntity<AssignmentResponse> create(@PathVariable Long sectionId, @Valid @RequestBody CreateAssignmentRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(sectionId, request));
    }

    // get the assignment of a section
    @GetMapping("/api/sections/{sectionId}/assignment")
    public ResponseEntity<AssignmentResponse> getForSection(@PathVariable Long sectionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getForSection(sectionId));
    }

    // submit a solution of assignment for a section.
    // we use the assignmentId to retrieve which section it belongs to
    @PostMapping("/api/assignments/{assignmentId}/submissions")
    public ResponseEntity<SubmissionResponse> submit(@PathVariable Long assignmentId, @RequestParam @NotNull Long studentId,
                                                     @Valid @RequestBody CreateSubmissionRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.submit(assignmentId, studentId, request));
    }

    // get the submission of an assignment
    @GetMapping("/api/assignments/{assignmentId}/submissions")
    public ResponseEntity<List<SubmissionResponse>> submissions(@PathVariable Long assignmentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.submissions(assignmentId));
    }
}
