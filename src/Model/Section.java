package Model;

import java.util.ArrayList;
import java.util.List;

public class Section extends Subject{
    private final List<Student> students = new ArrayList<>();

    public Section(String label) {
        super(label, 1);
        this.setChildren(new ArrayList<>());
    }

    public List<Student> getStudents() {
        return students;
    }
}
