package org.spring.lms.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    long countBySectionCourseId(Long courseId);
    List<Assignment> findAllBySectionIdOrderByDueAtAscIdAsc(Long sectionId);

}
