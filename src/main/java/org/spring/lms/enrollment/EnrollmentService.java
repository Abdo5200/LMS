package org.spring.lms.enrollment;

import jakarta.persistence.EntityNotFoundException;
import org.spring.lms.course.Course;
import org.spring.lms.course.CourseRepository;
import org.spring.lms.user.UserAccount;
import org.spring.lms.user.UserRepository;
import org.spring.lms.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollments;
    private final UserRepository users;
    private final CourseRepository courses;
    private final EnrollmentMapper mapper;

    public EnrollmentService(EnrollmentRepository enrollments, UserRepository users,
                             CourseRepository courses, EnrollmentMapper mapper) {
        this.enrollments = enrollments;
        this.users = users;
        this.courses = courses;
        this.mapper = mapper;
    }

    public EnrollmentResponse enroll(EnrollmentRequest enrollmentRequest) {
        Long studentId = enrollmentRequest.studentId();
        Long courseId = enrollmentRequest.courseId();

        UserAccount student = users
                .findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student " + studentId + " was not found."));

        if (student.getRole() != UserRole.STUDENT)
            throw new IllegalStateException("Only student accounts can enroll.");

        Course course = courses
                .findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course " + courseId + " was not found."));

        if (!course.isPublished())
            throw new IllegalStateException("Only published courses can be joined.");

        if (enrollments.existsByStudentIdAndCourseId(studentId, courseId))
            throw new IllegalStateException("This student is already enrolled in the course.");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return mapper.toResponse(enrollments.save(enrollment));
    }

    public List<EnrollmentResponse> getStudentEnrollments(Long studentId) {
        if (!users.existsById(studentId))
            throw new EntityNotFoundException("Student " + studentId + " was not found.");

        return enrollments
                .findAllByStudentIdOrderByEnrolledAtDesc(studentId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
