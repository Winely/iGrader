package Model;

import java.util.ArrayList;
import java.util.List;

public class Class extends Subject{
    private List<Section> sections = new ArrayList<>();
    private String label;

    public Class(String label) {
        super(label, 1);
        this.setChildren(new ArrayList<>());
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void removeSection(Section section) {
        sections.remove(section);
    }

    public String toString() {
        return label;
    }
}
