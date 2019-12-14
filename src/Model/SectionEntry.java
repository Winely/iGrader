package Model;

import java.util.HashMap;
import java.util.Map;

public class SectionEntry {

    private Student student;
    private Subject scheme;
    public Name name;
    public Map<String, Score> scoreMap = new HashMap<>();

    public SectionEntry(Student student, Subject scheme) {
        this.student = student;
        this.name = student.getName();
        this.scheme = scheme;
        pullSubjectInfo();
    }

    private void pullSubjectInfo() {
        for (Subject subject: scheme.getChildren()) {
            Score score = new Score(85.0, 0.0);
//                Score score = subject.getScoreByStudentId(student);
            scoreMap.put(subject.getLabel(), score);
        }
    }


}
