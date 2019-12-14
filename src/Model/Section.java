package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Section extends BaseEntity{
    private int id;
    private String label;
    private Course course;
    private double curve;
    private List<Student> students;

    public Section() {}

    public Section(String label) {
        this(new ArrayList<>(), label);
    }

    public Section(List<Student> students, String label) {
        this.students = students;
        this.label = label;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "label", nullable = false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, course.getId());
    }

    @ManyToOne
    @JoinColumn(name = "course", referencedColumnName = "id", nullable = false)
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Basic
    public double getCurve() {
        return curve;
    }

    public void setCurve(double curve) {
        this.curve = curve;
    }
}
