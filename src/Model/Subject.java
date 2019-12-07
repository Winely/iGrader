package Model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Subject extends BaseEntity implements Commentable{
    private int id;
    private String label = "";
    private double weight;
    private Map<String, Grade> grades;
    private Subject parent;
    private List<Subject> children;
    private Score maxScore = new Score(100, 0);
    private String comment = "";

    public Score getScoreByStudentId(String studentId){
        if (children.isEmpty()){
            return grades.get(studentId).getScore()
                    .times(100*weight/(maxScore.getPoint()));
        }
        return children.stream()
                .map(child -> child.getScoreByStudentId(studentId))
                .reduce(Score::addScore)
                .orElse(Score.ZERO)
                .times(weight);
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
    @Column(name = "label", nullable = false, length = 255)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "weight", nullable = false, precision = 0)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "comment", nullable = false, length = 255)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, maxScore, weight, comment);
    }

    @MapKey(name = "student")
    @OneToMany(mappedBy = "subject")
    public Map<String, Grade> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Grade> grades) {
        this.grades = grades;
    }

    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "id")
    public Subject getParent() {
        return parent;
    }

    public void setParent(Subject parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public List<Subject> getChildren() {
        return children;
    }

    public void setChildren(List<Subject> children) {
        this.children = children;
    }

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "point", column = @Column(name = "maxpoint", nullable = false, precision = 0)),
            @AttributeOverride(name = "bonus", column = @Column(name = "maxbonus", nullable = false, precision = 0))
    })
    public Score getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Score maxScore) {
        this.maxScore = maxScore;
    }
}
