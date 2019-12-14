package Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends BaseEntity{
    private int id;
    private Subject scheme = new Subject();
    private List<Section> sections = new ArrayList<>();

    public Course() {
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "scheme", referencedColumnName = "id", nullable = false)
    public Subject getScheme() {
        return scheme;
    }

    public void setScheme(Subject scheme) {
        this.scheme = scheme;
    }

    @OneToMany(mappedBy = "course")
    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        this.sections.add(section);
    }
}
