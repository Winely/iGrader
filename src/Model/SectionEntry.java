package Model;

import java.util.HashMap;
import java.util.Map;

public class SectionEntry {

    public Student student;
    public Subject scheme;
    public Name name;
    public Map<String, Grade> scoreMap = new HashMap<>();

    public SectionEntry(Student student, Subject scheme) {
        this.student = student;
        this.name = student.getName();
        this.scheme = scheme;
        pullSubjectInfo();
    }

    private void pullSubjectInfo() {
        for (Subject subject: scheme.getChildren()) {
            Grade grade = subject.getGrades().get(student);
            scoreMap.put(subject.getLabel(), grade);
        }
    }

    public Student getStudent() {
        return student;
    }
}
