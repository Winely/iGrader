package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SectionEntry {
    private final ObjectProperty<Student> student = new SimpleObjectProperty<>();
    private final BooleanProperty frozen = new SimpleBooleanProperty(false);
    public Subject scheme;
    public Name name;
    public Map<String, Grade> scoreMap = new HashMap<>();

    public SectionEntry(Student student, Subject scheme) {
        this.student.setValue(student);
        this.name = student.getName();
        this.scheme = scheme;
        this.frozen.setValue(student.isFrozen());
        pullSubjectInfo();
    }

    private void pullSubjectInfo() {
        for (Subject subject: scheme.getChildren()) {
            Grade grade;
            if (subject.getChildren().size() == 0) {
                grade = subject.getGrades().getOrDefault(student.getValue(), new Grade(student.getValue(), subject, Score.ZERO));
            } else {
                Score score = subject.getScoreByStudent(student.getValue());
                grade = new Grade(student.getValue(), subject, score);
            }
            scoreMap.put(subject.getLabel(), grade);
        }
    }

    public Student getStudent() {
        return student.getValue();
    }

    public ObjectProperty<Student> studentProperty(){
        return student;
    }

    public BooleanProperty frozenProperty(){
        return frozen;
    }

    public boolean isFrozen() {
        return frozen.get();
    }
}
