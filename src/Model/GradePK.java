package Model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GradePK implements Serializable {
    private String student;
    private int subject;

    @Basic
    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    @Basic
    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradePK gradePK = (GradePK) o;
        return subject == gradePK.subject &&
                student.equals(gradePK.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, subject);
    }
}
