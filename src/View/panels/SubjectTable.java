package View.panels;

import Model.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import sample.Main;
import View.components.*;
//
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectTable extends TableView<SubjectTable.SubjectEntry> {

    private Subject subject;
    private ArrayList<Name> names = new ArrayList<>();

    public SubjectTable(Section subject) {
        super();
        this.subject = subject;
        initStudentNames();

        ObservableList<SubjectEntry> entries = FXCollections.observableArrayList();

        for (Name name: names) {
            entries.add(new SubjectEntry(name));
        }

        ArrayList<TableColumn> columns = new ArrayList<>();
        // create name column
        TableColumn<SubjectEntry, Name> nameCol = new TableColumn<>("Names");
        columns.add(nameCol);

        // create columns for every assignment that is part of this subject
        for (Subject sub: subject.getChildren()) {
            String label = sub.getLabel();
            TableColumn<SubjectEntry, String> subCol = new TableColumn<>(label);
            subCol.setCellValueFactory(new PropertyValueFactory<>(String.format("scoreMap.get(%s)", label)));
            columns.add(subCol);
        }

        setItems(entries);
        for (TableColumn column: columns) {
            getColumns().add(column);
        }

    }

    private void initStudentNames() {
        for (Student student: subject.getGrades().keySet()) {
            names.add(student.getName());
        }
    }


    class SubjectEntry {
        private Name name;
        private Map<String, Score> scoreMap = new HashMap<>();

        public SubjectEntry(Name name) {
            this.name = name;
            pullSubjectInfo();
        }

        private void pullSubjectInfo() {
            for (Subject subject: subject.getChildren()) {
                Score score = findScoreByName(subject.getGrades());
                scoreMap.put(subject.getLabel(), score);
            }
        }

        private Score findScoreByName(Map<Student, Score> grades) {
            for (Student student: grades.keySet()) {
                if (student.getName().equals(name)) {
                    return grades.get(student);
                }
            }
            return null;
        }

    }

}

// cell span --> https://stackoverflow.com/questions/50327551/how-to-merge-tableview-cells-in-javafx
