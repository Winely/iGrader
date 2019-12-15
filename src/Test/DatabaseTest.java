package Test;

import java.util.ArrayList;
import java.util.List;

import Database.DAO;
import Model.*;

public class DatabaseTest {
    public static void main(String[] args) {
//        initData();   // uncomment this line if you have no data in local DB
        initGrades();
        Course cs591 = new DAO().findById(Course.class, 1);
        Student alice = cs591.getSections().get(0).getStudents().get(0);
        Score score = cs591.getScheme().getChildren().get(0).getFinalScoreByStudent(alice);
        assert score.getPoint()==100;
        assert score.getBonus()==10;
        System.out.println("Hello");
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
    
    private static void initGrades(){
    	
    	Grade grade = new Grade();
    	Course cs591 = new DAO().findById(Course.class, 1);
    	Section section = cs591.getSections().get(0);
        
        Student johnWick = new Student("Grad1", new Name("John","","Wick"), true);
        johnWick.setSection(section);
        johnWick.save();
		Student jamesBond = new Student("Grad2", new Name("James","","Bond"), true);
		jamesBond.setSection(section);
		jamesBond.save();
		Student samWinchester = new Student("Undergrad1", new Name("Sam","","Winchester"), false);
		samWinchester.setSection(section);
		samWinchester.save();
		Student sherlockHolmes = new Student("Undergrad2", new Name("Sherlock","","Holmes"), false);
		sherlockHolmes.setSection(section);
		sherlockHolmes.save();

		Subject subject = section.getCourse().getScheme();
		Subject homework1 = subject.getChildren().get(0).getChildren().get(0);
		Subject homework2 = subject.getChildren().get(0).getChildren().get(1);
		
		grade.setSubject(homework1);
        grade.setStudent(johnWick);
        grade.setScore(new Score(75, 0));
        grade.save();
        grade.setStudent(jamesBond);
        grade.setScore(new Score(80, 0));
        grade.save();
        grade.setStudent(samWinchester);
        grade.setScore(new Score(85, 0));
        grade.save();
        grade.setStudent(sherlockHolmes);
        grade.setScore(new Score(90, 0));
        grade.save();
        
        grade.setSubject(homework2);
        grade.setStudent(johnWick);
        grade.setScore(new Score(50, 0));
        grade.save();
        grade.setStudent(jamesBond);
        grade.setScore(new Score(60, 0));
        grade.save();
        grade.setStudent(samWinchester);
        grade.setScore(new Score(70, 0));
        grade.save();
        grade.setStudent(sherlockHolmes);
        grade.setScore(new Score(80, 0));
        grade.save();
        
        Subject project = subject.getChildren().get(1);
        
        grade.setSubject(project);
        grade.setStudent(johnWick);
        grade.setScore(new Score(25, 0));
        grade.save();
        grade.setStudent(jamesBond);
        grade.setScore(new Score(50, 0));
        grade.save();
        grade.setStudent(samWinchester);
        grade.setScore(new Score(75, 0));
        grade.save();
        grade.setStudent(sherlockHolmes);
        grade.setScore(new Score(100, 0));
        grade.save();
        
    }
}
