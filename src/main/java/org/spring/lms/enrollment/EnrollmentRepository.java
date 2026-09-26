package org.spring.lms.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    boolean existsByStudentIdAndCourseIdAndStatus(Long studentId, Long courseId, EnrollmentStatus status);
    List<Enrollment> findAllByStudentIdOrderByEnrolledAtDesc(Long studentId);
    Optional<Enrollment> findByIdAndStudentId(Long id, Long studentId);

}
