package Model;

public class Student extends Commentable{
    private final String id;
    private final Name name;
    private boolean isGrad;

    public Student(String id, Name name, boolean isGrad) {
        this.id = id;
        this.name = name;
        this.isGrad = isGrad;
    }

    public boolean isGrad() {
        return isGrad;
    }

    public void setGrad(boolean grad) {
        isGrad = grad;
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
