package org.spring.lms.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    boolean existsByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
    List<Submission> findAllByAssignmentIdOrderBySubmittedAtAsc(Long assignmentId);
}
