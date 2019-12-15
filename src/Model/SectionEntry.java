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
    private Subject scheme;
    public Name name;
    public Map<String, Score> scoreMap = new HashMap<>();

    public SectionEntry(Student student, Subject scheme) {
        this.student.setValue(student);
        this.name = student.getName();
        this.scheme = scheme;
        this.frozen.setValue(student.isFrozen());
        pullSubjectInfo();
    }

    private void pullSubjectInfo() {
        for (Subject subject: scheme.getChildren()) {
            Score score = new Score(85.0, 0.0);
//                Score score = subject.getScoreByStudentId(student);
            scoreMap.put(subject.getLabel(), score);
        }
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
