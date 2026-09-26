package org.spring.lms.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.lms.user.UserAccount;

@Entity
@Table(name = "course_sections", uniqueConstraints = @UniqueConstraint(name = "uk_section_position", columnNames = {"course_id", "position"}))
@Getter @Setter @NoArgsConstructor
public class CourseSection {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ta_id", nullable = false)
    private UserAccount teachingAssistant;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(name = "sheet_url", nullable = false, length = 1000)
    private String sheetUrl;

    @Column(name = "position", nullable = false)
    private int position;
}
