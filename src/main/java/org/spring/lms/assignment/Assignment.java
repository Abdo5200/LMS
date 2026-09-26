package org.spring.lms.assignment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.lms.course.CourseSection;

import java.time.Instant;

@Entity
@Table(name = "assignments", uniqueConstraints = @UniqueConstraint(name = "uk_assignment_section", columnNames = "section_id"))
@Getter @Setter @NoArgsConstructor
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "section_id", nullable = false, unique = true)
    private CourseSection section;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, length = 4000)
    private String instructions;

    @Column(name = "due_at")
    private Instant dueAt;
}
