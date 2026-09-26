package org.spring.lms.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    boolean existsBySectionId(Long sectionId);
    long countBySectionCourseId(Long courseId);
    Optional<Assignment> findBySectionId(Long sectionId);

}
