package org.spring.lms.course;

import jakarta.persistence.EntityNotFoundException;
import org.spring.lms.user.UserAccount;
import org.spring.lms.user.UserRepository;
import org.spring.lms.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseContentService {
    private final CourseService courses;
    private final SectionRepository sections;
    private final LectureRepository lectures;
    private final UserRepository users;
    private final CourseInstructorRepository courseInstructors;
    private final SectionMapper sectionMapper;
    private final LectureMapper lectureMapper;

    public CourseContentService(CourseService courses, SectionRepository sections, LectureRepository lectures,
                                UserRepository users, CourseInstructorRepository courseInstructors,
                                SectionMapper sectionMapper, LectureMapper lectureMapper) {
        this.courses = courses;
        this.sections = sections;
        this.lectures = lectures;
        this.users = users;
        this.courseInstructors = courseInstructors;
        this.sectionMapper = sectionMapper;
        this.lectureMapper = lectureMapper;
    }

    public SectionResponse addSection(Long courseId, CreateSectionRequest request) {
        Course course = courses.find(courseId);

        UserAccount ta = users
                .findById(request.teachingAssistantId())
                .orElseThrow(() -> new EntityNotFoundException("Teaching assistant " + request.teachingAssistantId() + " was not found."));

        if (ta.getRole() != UserRole.TEACHING_ASSISTANT)
            throw new IllegalStateException("The selected account is not a teaching assistant.");

        CourseSection section = sectionMapper.toEntity(request);
        section.setTitle(section.getTitle().trim());
        section.setSheetUrl(section.getSheetUrl().trim());
        section.setCourse(course);
        section.setTeachingAssistant(ta);

        return sectionMapper.toResponse(sections.save(section));
    }

    @Transactional(readOnly = true)
    public List<SectionResponse> sections(Long courseId) {
        courses.find(courseId);
        return sections
                .findAllByCourseIdOrderByPosition(courseId)
                .stream()
                .map(sectionMapper::toResponse)
                .toList();
    }

    public LectureResponse addLecture(Long courseId, CreateLectureRequest request) {
        Course course = courses.find(courseId);
        UserAccount instructor = users
                .findById(request.instructorId())
                .orElseThrow(() -> new EntityNotFoundException("Professor " + request.instructorId() + " was not found."));

        if (instructor.getRole() != UserRole.INSTRUCTOR || !courseInstructors.existsByCourseIdAndInstructorId(courseId, instructor.getId())) {
            throw new IllegalStateException("A lecture must be taught by one of this course's professors.");
        }

        Lecture lecture = lectureMapper.toEntity(request);
        lecture.setTitle(lecture.getTitle().trim());
        lecture.setPresentationUrl(lecture.getPresentationUrl().trim());
        lecture.setCourse(course);
        lecture.setInstructor(instructor);

        return lectureMapper.toResponse(lectures.save(lecture));
    }

    @Transactional(readOnly = true)
    public List<LectureResponse> lectures(Long courseId) {
        courses.find(courseId);
        return lectures
                .findAllByCourseIdOrderByPosition(courseId)
                .stream()
                .map(lectureMapper::toResponse)
                .toList();
    }

    private void requireDraft(Course course) {
        if (course.isPublished())
            throw new IllegalStateException("Published course content cannot be changed.");
    }
}
