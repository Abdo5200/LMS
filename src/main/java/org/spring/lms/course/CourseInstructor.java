package org.spring.lms.course;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.lms.user.UserAccount;

@Entity
@Table(name = "course_instructors")
@Data
@NoArgsConstructor
public class CourseInstructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private UserAccount instructor;
}
