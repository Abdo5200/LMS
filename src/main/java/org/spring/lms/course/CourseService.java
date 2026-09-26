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
public class CourseService {
    private final CourseRepository courses;
    private final UserRepository users;
    private final CourseMapper mapper;
    private final LectureRepository lectures;

    public CourseService(CourseRepository courses, UserRepository users, CourseMapper mapper,
                         LectureRepository lectures) {
        this.courses = courses;
        this.users = users;
        this.mapper = mapper;
        this.lectures = lectures;
    }

    public CourseResponse create(CreateCourseRequest request) {

        if (request.instructorIds().stream().distinct().count() != request.instructorIds().size()) {
            throw new IllegalStateException("A professor can only be assigned once to a course.");
        }

        Course course = mapper.toEntity(request);
        course.setTitle(course.getTitle().trim());
        course.setDescription(course.getDescription().trim());

        for (Long instructorId : request.instructorIds()) {

            UserAccount instructor = users
                    .findById(instructorId)
                    .orElseThrow(() -> new EntityNotFoundException("Professor " + instructorId + " was not found."));

            if (instructor.getRole() != UserRole.INSTRUCTOR)
                throw new IllegalStateException("Every course professor must have the instructor role.");

            CourseInstructor courseInstructor = new CourseInstructor();
            courseInstructor.setCourse(course);
            courseInstructor.setInstructor(instructor);
            course.getInstructors().add(courseInstructor);

        }

        return mapper.toResponse(courses.save(course));
    }

    public List<CourseResponse> getAllCourses() {
        return courses
                .findAllByPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CourseResponse getCourse(Long id) {
        return mapper.toResponse(find(id));
    }

    @Transactional
    public CourseResponse changeInstructor(Long id, ChangeCourseInstructorRequest request) {
        Course course = find(id);

        if (request.oldInstructorId().equals(request.newInstructorId())) {
            throw new IllegalStateException("Choose a different professor to replace the current one.");
        }

        CourseInstructor oldInstructor = course
                .getInstructors()
                .stream()
                .filter(courseInstructor -> courseInstructor.getInstructor().getId().equals(request.oldInstructorId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Professor " + request.oldInstructorId() + " is not assigned to this course."));

        UserAccount replacement = users
                .findById(request.newInstructorId())
                .orElseThrow(() -> new EntityNotFoundException("Professor " + request.newInstructorId() + " was not found."));

        if (replacement.getRole() != UserRole.INSTRUCTOR) {
            throw new IllegalStateException("The replacement account must have the instructor role.");
        }

        boolean replacementAlreadyAssigned = course
                .getInstructors()
                .stream()
                .anyMatch(courseInstructor -> courseInstructor.getInstructor().getId().equals(replacement.getId()));

        for (Lecture lecture : lectures.findAllByCourseIdOrderByPosition(id)) {
            if (lecture.getInstructor().getId().equals(request.oldInstructorId())) {
                lecture.setInstructor(replacement);
            }
        }

        if (replacementAlreadyAssigned) {
            course.getInstructors().remove(oldInstructor);
            return mapper.toResponse(course);
        }

        course.getInstructors().remove(oldInstructor);

        CourseInstructor newLink = new CourseInstructor();
        newLink.setCourse(course);
        newLink.setInstructor(replacement);
        course.getInstructors().add(newLink);

        return mapper.toResponse(course);
    }

    public CourseResponse publish(Long id) {
        Course course = find(id);
        if (course.getInstructors().isEmpty()) {
            throw new IllegalStateException("Assign at least one professor before publishing.");
        }
        course.setPublished(true);
        return mapper.toResponse(course);
    }

    Course find(Long id) {
        return courses
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course " + id + " was not found."));
    }
}
