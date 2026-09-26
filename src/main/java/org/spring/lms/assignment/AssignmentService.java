package org.spring.lms.assignment;

import jakarta.persistence.EntityNotFoundException;
import org.spring.lms.course.CourseSection;
import org.spring.lms.course.SectionRepository;
import org.spring.lms.enrollment.EnrollmentRepository;
import org.spring.lms.enrollment.EnrollmentStatus;
import org.spring.lms.user.UserAccount;
import org.spring.lms.user.UserRepository;
import org.spring.lms.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository assignments;
    private final SubmissionRepository submissions;
    private final SectionRepository sections;
    private final UserRepository users;
    private final EnrollmentRepository enrollments;
    private final AssignmentMapper mapper;

    public AssignmentService(AssignmentRepository assignments, SubmissionRepository submissions,
                             SectionRepository sections, UserRepository users,
                             EnrollmentRepository enrollments, AssignmentMapper mapper) {
        this.assignments = assignments;
        this.submissions = submissions;
        this.sections = sections;
        this.users = users;
        this.enrollments = enrollments;
        this.mapper = mapper;
    }

    public AssignmentResponse create(Long sectionId, CreateAssignmentRequest request) {
        CourseSection section = sections
                .findById(sectionId)
                .orElseThrow(() -> new EntityNotFoundException("Section " + sectionId + " was not found."));

        Assignment assignment = mapper.toEntity(request);
        assignment.setTitle(assignment.getTitle().trim());
        assignment.setInstructions(assignment.getInstructions().trim());
        assignment.setSection(section);

        return mapper.toResponse(assignments.save(assignment));
    }

    @Transactional(readOnly = true)
    public AssignmentResponse getForSection(Long sectionId) {
        return assignments.findAllBySectionIdOrderByDueAtAscIdAsc(sectionId).stream()
                .findFirst()
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("No assignment exists for section " + sectionId + "."));
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponse> getAllForSection(Long sectionId) {
        if (!sections.existsById(sectionId))
            throw new EntityNotFoundException("Section " + sectionId + " was not found.");
        return assignments.findAllBySectionIdOrderByDueAtAscIdAsc(sectionId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SubmissionResponse submit(Long assignmentId, Long studentId, CreateSubmissionRequest request) {

        Assignment assignment = assignments
                .findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment " + assignmentId + " was not found."));

        UserAccount student = users
                .findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student " + studentId + " was not found."));

        if (student.getRole() != UserRole.STUDENT)
            throw new IllegalStateException("Only students can submit this work.");

        Long courseId = assignment.getSection().getCourse().getId();
        if (!assignment.getSection().getCourse().isPublished() ||
                !enrollments.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE)) {
            throw new IllegalStateException("An active enrollment in the published course is required to submit work.");
        }

        if (submissions.existsByAssignmentIdAndStudentId(assignmentId, studentId)) {
            throw new IllegalStateException("A submission has already been made for this assignment.");
        }

        Submission submission = mapper.toEntity(request);
        submission.setAssignment(assignment);
        submission.setStudent(student);

        return mapper.toResponse(submissions.save(submission));
    }

    @Transactional(readOnly = true)
    public List<SubmissionResponse> submissions(Long assignmentId) {
        if (!assignments.existsById(assignmentId))
            throw new EntityNotFoundException("Assignment " + assignmentId + " was not found.");

        return submissions
                .findAllByAssignmentIdOrderBySubmittedAtAsc(assignmentId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
