package Model;

import javax.persistence.*;

@Entity
public class Student extends BaseEntity implements Commentable{
    private String id;
    private Name name;
    private String comment = "";
    private boolean isGrad;
    private Section section;

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
}
