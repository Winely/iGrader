package Controller;

import Database.DAO;
import Model.Course;
import Model.Section;
import Model.Subject;

public class CourseController {

    public Course createNewCourse(String label, Subject template) {
        Course course = new Course();
        Subject newSubject = template.duplicateSubject();
        newSubject.setLabel(label);
        newSubject.update();
        course.setScheme(newSubject);
        course.save();
        return course;
    }

    public void addSection(Course course) {
        int num = course.getSections().size()+1;
        String label = "Section " + num;
        Section section = new Section();
        section.setCourse(course);
        section.setLabel(label);
        section.save();
        course.refresh();
    }

    public static void removeSection(Section section) {
        new DAO().delete(Section.class, section.getId());
    }

    public static void removeCourse(Course course){
        new DAO().delete(Course.class, course.getId());
    }
}
