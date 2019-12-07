package Model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class Name {
    private String firstName;
    private String midName;
    private String lastName;
    private String fullName;

    public Name() { }
    public Name(String firstName, String midName, String lastName){
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.fullName = getFullName();
    }

    @Basic
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Basic
    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }


    @Basic
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String fullName(){
        return String.format("%s %s %s", firstName, midName, lastName);
    }

    @Override
    public String toString() {
        return fullName();
    }
}
