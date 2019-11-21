package Model;

import java.util.ArrayList;
import java.util.List;

public class Class extends Subject{
    private final List<Student> students = new ArrayList<>();

    public Class(String label) {
        super(label, 1);
        this.setChildren(new ArrayList<>());
    }

    public List<Student> getStudents() {
        return students;
    }
}
