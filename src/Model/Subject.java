package Model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
public class Subject extends BaseEntity implements Commentable{
    private int id;
    private String label = "";
    private double weight;
    private Map<Student, Grade> grades = new HashMap<>();
    private Subject parent;
    private List<Subject> children = new ArrayList<>();
    private Score maxScore = new Score(100, 0);
    private String comment = "";

    public Subject(){}
    public Subject(String label){
        this.label = label;
    }

    public Score getScoreByStudent(Student student) {
        if (children.isEmpty()){
            Grade grade = grades.get(student);
            if (grade == null) {
                return new Score(0, 0);
            }
            return grade.getScore()
                    .times(100*weight/(maxScore.getPoint()));
        }
        return children.stream()
                .map(child -> child.getScoreByStudent(student))
                .reduce(Score::addScore)
                .orElse(Score.ZERO)
                .times(weight);
    }

    public Score getFinalScoreByStudent(Student student){
        Score total = getScoreByStudent(student);
        total.setPoint(total.getPoint() + student.getSection().getCurve());
        return total;
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

    @MapKeyJoinColumn(name = "student")
    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    public Map<Student, Grade> getGrades() {
        return grades;
    }

    public void setGrades(Map<Student, Grade> grades) {
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
    @LazyCollection(LazyCollectionOption.FALSE)
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

    public Subject duplicateSubject() {
        Subject copy = new Subject();
        copy.setLabel(label);
        copy.setWeight(weight);
        copy.setMaxScore(maxScore);

        if (parent != null) {
//            copyParent.setLabel(parent.label);
//            copyParent.setWeight(parent.weight);
            copy.parent = parent.duplicateSubject();
        }

        for (Subject child: children) {

            Subject copyChild = child.duplicateSubject();
            copyChild.parent = copy;

        }

        return copy;
    }
}
