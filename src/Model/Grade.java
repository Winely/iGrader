package Model;

import javax.persistence.*;

@Entity
@IdClass(GradePK.class)
public class Grade extends BaseEntity implements Commentable{
    private String comment = "";
    private Score score;
    private Subject subject;
    private Student student;

    public Grade(){}
    public Grade(Student student, Subject subject, Score score){
        this.student = student;
        this.subject = subject;
        this.score = score;
    }

    @Basic
    @Column(name = "comment", nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne
    @JoinColumn(name = "subject", referencedColumnName = "id", nullable = false)
    @Id
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @ManyToOne
    @JoinColumn(name = "student", referencedColumnName = "id", nullable = false)
    @Id
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "point", column = @Column(nullable = true, precision = 0)),
            @AttributeOverride(name = "bonus", column = @Column(nullable = true, precision = 0))
    })
    public Score getScore() {
        return score;
    }

    public void setScore(Score grade) {
        this.score = grade;
    }
}
