package Test;

import Database.DAO;
import Model.*;

public class DatabaseTest {
    public static void main(String[] args) {
        initData();   // uncomment this line if you have no data in local DB
        Course cs591 = new DAO().findById(Course.class, 1);
        Student alice = cs591.getSections().get(0).getStudents().get(0);
        Score score = cs591.getScheme().getChildren().get(0).getFinalScoreByStudent(alice);
        assert score.getPoint()==100;
        assert score.getBonus()==10;
    }
    private static void initData(){
        Subject subject = new Subject("CS591");
        subject.save();

        Subject hw = new Subject("Homework");
        hw.setParent(subject);
        hw.setWeight(20);
        hw.save();

        Subject proj = new Subject("Project");
        proj.setParent(subject);
        proj.setWeight(80);
        proj.save();

        Course course = new Course();
        course.setScheme(subject);
        course.save();

        Section section = new Section("A1");
        section.setCourse(course);
        section.save();

        Student student = new Student(
                "U123456",
                new Name("Alice", "", "A"),
                false);
        student.setSection(section);
        section.setCourse(course);
        student.save();

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(hw);
        grade.setScore(new Score(100, 10));
        grade.save();
    }
}
