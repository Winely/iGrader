package Controller;

import Model.Course;
import Model.Section;
import Model.Subject;
import sample.Main;

import java.util.ArrayList;
import java.util.List;

public class CourseController {

    public void createNewCourse(String label, Subject template) {
        Course course = new Course();
        Subject newSubject = template.duplicateSubject();
        newSubject.setLabel(label);
        course.setScheme(newSubject);
        newSubject.save();
        course.save();
    }

    public void addSection(Course course) {
        String label = "Section " + course.getSections().size();
        Section section = new Section();
        section.setCourse(course);
        section.setLabel(label);
        section.save();
        course.refresh();
    }

}
