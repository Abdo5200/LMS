package org.spring.lms.course;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllByCourseIdOrderByPosition(Long courseId);
    long countByCourseId(Long courseId);
}
