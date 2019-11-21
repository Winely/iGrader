package Model;

import java.util.*;

public class Subject extends Commentable {
    private String label;
    private Score maxScore = new Score(100, 0);
    private double weight;
    private Date issuedDate;
    private Date dueDate;
    private List<Subject> children;
    private Map<Student, Score> grades = new HashMap<>();

    public Subject(String label, double weight) {
        this.label = label;
        this.weight = weight;
    }

    public Score getScore(Student student){
        if (children == null){
            return grades.getOrDefault(student, Score.ZERO)
                    .times(100*weight/(maxScore.getPoints()));
        }
        return children.stream()
                .map(part -> part.getScore(student))
                .reduce(Score::addScore)
                .orElse(Score.ZERO)
                .times(weight);
    }

    public void setScore(Student student, Score score){
        if (children != null){
            // TODO: throw an error
            return;
        }
        if (score.isOver(maxScore)){
            // TODO: throw an error
            return;
        }
        grades.put(student, score);
    }

    public void addChild(Subject child){
        if (children == null){
            children = new ArrayList<>();   // lazy init
        }
        children.add(child);
    }

    // Getters and Setters
    public List<Subject> getChildren() {
        return children;
    }

    public void setChildren(List<Subject> children) {
        this.children = children;
    }

    public Map<Student, Score> getGrades() {
        return grades;
    }

    public void setGrades(Map<Student, Score> grades) {
        this.grades = grades;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Score getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Score maxScore) {
        this.maxScore = maxScore;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
