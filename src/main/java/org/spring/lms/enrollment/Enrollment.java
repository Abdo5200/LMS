package org.spring.lms.enrollment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.lms.course.Course;
import org.spring.lms.user.UserAccount;

import java.time.Instant;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(name = "uk_enrollment_student_course",
                columnNames = {"student_id", "course_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private UserAccount student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(name = "enrolled_at", nullable = false, updatable = false)
    private Instant enrolledAt;

    @PrePersist
    void onCreate() {
        enrolledAt = Instant.now();
    }
}
