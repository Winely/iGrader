package Model;

public class Score{
    public static final Score ZERO = new Score(0, 0);
    private double points;
    private double bonus;

    public Score(double points, double bonus) {
        this.points = points;
        this.bonus = bonus;
    }

    public static Score addScore(Score score1, Score score2){
        return new Score(score1.getPoints()+score2.getPoints(), score1.getBonus() + score2.getBonus());
    }

    public Score times(double weight){
        return new Score(points *weight, bonus);
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public boolean isOver(Score o) {
        return (points > o.getPoints() || bonus > o.getBonus());
    }
}
