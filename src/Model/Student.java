package Model;

import java.util.Objects;

import javax.persistence.*;

@Entity
public class Student extends BaseEntity implements Commentable{
    private String id;
    private Name name;
    private String comment = "";
    private boolean isGrad;
    private Section section;
    private boolean frozen;

    public Student() {
    }

    public Student(String id, Name name, boolean isGrad){
        this.id = id;
        this.name = name;
        this.isGrad = isGrad;
    }

    @Id
    @Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "comment", nullable = false)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "isgrad", nullable = false)
    public boolean isGrad() {
        return isGrad;
    }

    public void setGrad(boolean isGrad) {
        this.isGrad = isGrad;
    }

    @ManyToOne
    @JoinColumn(name = "section", referencedColumnName = "id", nullable = false)
    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "firstName", column = @Column(name = "firstname", nullable = false)),
            @AttributeOverride(name = "midName", column = @Column(name = "midname", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "lastname", nullable = false))
    })
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Basic
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /***
     * Returning a new student object from the data line like
     * "U123456,Alice,D,AA,false"
     */
    public static Student fromCSVLine(String line){
        try {
            String[] values = line.split(",");
            String id = values[0];
            String firstName = values[1];
            String midName = values[2];
            String lastName = values[3];
            boolean isGrad = Boolean.parseBoolean(values[4]);
            return new Student(id, new Name(firstName, midName, lastName), isGrad);
        } catch (Exception e){
            return null;
        }
    }
}
