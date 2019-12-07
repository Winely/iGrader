package Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course extends BaseEntity{
    private int id;
    private Subject scheme;
    private List<Section> sections;

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
}
