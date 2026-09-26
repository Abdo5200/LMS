package org.spring.lms.assignment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.lms.user.UserAccount;

import java.time.Instant;

@Entity
@Table(name = "submissions", uniqueConstraints = @UniqueConstraint(
        name = "uk_submission_assignment_student", columnNames = {"assignment_id", "student_id"}))
@Getter @Setter @NoArgsConstructor
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private UserAccount student;

    @Column(name = "submission_url", nullable = false, length = 1000)
    private String submissionUrl;

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private Instant submittedAt;

    @Column
    private Integer grade;

    @Column(length = 4000)
    private String feedback;

    @PrePersist
    void onCreate() { submittedAt = Instant.now(); }
}
