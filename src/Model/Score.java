package Model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class Score implements Comparable<Score>{
    public static final Score ZERO = new Score(0, 0);
    private double point;
    private double bonus;

    public Score() {
    }

    public Score(double point, double bonus){
        this.point = point;
        this.bonus = bonus;
    }

    @Basic
    public double getPoint() {
        return point;
    }

    public void setPoint(double score) {
        this.point = score;
    }

    @Basic
    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public static Score addScore(Score score1, Score score2){
        return new Score(score1.getPoint()+score2.getPoint(), score1.getBonus() + score2.getBonus());
    }

    public Score times(double weight){
        return new Score(point * weight, bonus);
    }

    public boolean isOver(Score o) {
        return (point > o.getPoint() || bonus > o.getBonus());
    }

    @Override
    public int compareTo(Score o) {
        return 0;
    }
}
