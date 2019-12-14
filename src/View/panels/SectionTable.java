package View.panels;
import Model.*;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Main;

import java.util.ArrayList;

public class SectionTable extends TableView<SectionEntry> {

    private Subject scheme;

    public SectionTable(Section section) {
        super();
//        this.section = section;
        this.scheme = section.getCourse().getScheme();

        System.out.println(section.getStudents());

        ObservableList<SectionEntry> entries = FXCollections.observableArrayList();

        for (Student student: section.getStudents()) {
            System.out.println(student);
            entries.add(new SectionEntry(student, scheme));
        }

        ArrayList<TableColumn> columns = new ArrayList<>();
        // create name column
        TableColumn<SectionEntry, Name> nameCol = new TableColumn<>("Names");
        nameCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().name));
        columns.add(nameCol);

        // create columns for every assignment that is part of this subject
        for (Subject sub: scheme.getChildren()) {
            String label = sub.getLabel();
            System.out.println(label);
            TableColumn<SectionEntry, Double> subCol = new TableColumn<>(label);
            subCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().scoreMap.get(label).getPoint()));
            columns.add(subCol);
        }

        for (TableColumn column: columns) {
            getColumns().add(column);
        }
        setItems(entries);
        getSelectionModel().setCellSelectionEnabled(true);

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    ObservableList<TablePosition> cells = getSelectionModel().getSelectedCells();
                    System.out.println(getSelectionModel().getSelectedItem().name);
                    System.out.println(getSelectionModel().getSelectedItem().scoreMap);
                    System.out.println(cells.get(0).getColumn());
                }
            }
        });

    }
}
