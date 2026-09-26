package org.spring.lms.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.lms.user.UserAccount;

@Entity
@Table(name = "lectures", uniqueConstraints = @UniqueConstraint(
        name = "uk_lecture_position", columnNames = {"course_id", "position"}))
@Getter @Setter @NoArgsConstructor
public class Lecture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    private UserAccount instructor;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(name = "presentation_url", nullable = false, length = 1000)
    private String presentationUrl;

    @Column(name = "position", nullable = false)
    private int position;
}
