package org.spring.lms.course;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SectionRepository extends JpaRepository<CourseSection, Long> {
    List<CourseSection> findAllByCourseIdOrderByPosition(Long courseId);
    long countByCourseId(Long courseId);
}
