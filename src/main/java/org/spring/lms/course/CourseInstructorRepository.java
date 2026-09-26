package org.spring.lms.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {
    boolean existsByCourseIdAndInstructorId(Long courseId, Long instructorId);
}
