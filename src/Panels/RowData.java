package Panels;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class RowData {
    private SimpleStringProperty components;
    private SimpleDoubleProperty weight;
    private SimpleDoubleProperty maxScore;
    private SimpleDoubleProperty maxBonus;
    
    public RowData(String components, Double weight, Double maxScore, Double maxBonus) {
        this.components = new SimpleStringProperty(components);
        this.weight = new SimpleDoubleProperty(weight);
        this.maxScore = new SimpleDoubleProperty(maxScore);
        this.maxBonus = new SimpleDoubleProperty(maxBonus);
    }
    
    public String getComponents() {return components.get();};
    public String getWeight() {return String.valueOf(weight.get());};
    public String getMaxScore() {return String.valueOf(maxScore.get());};
    public String getMaxBonus() {return String.valueOf(maxBonus.get());};
}
