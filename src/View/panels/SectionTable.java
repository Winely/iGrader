package View.panels;
import Model.*;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import sample.Main;

import java.util.ArrayList;
import java.util.Comparator;

public class SectionTable extends TableView<SectionEntry> {

    private Subject scheme;

    public SectionTable(Section section) {
        super();
        this.scheme = section.getCourse().getScheme();

        System.out.println(section.getStudents());

        ObservableList<SectionEntry> entries = FXCollections.observableArrayList();

        for (Student student: section.getStudents()) {
            System.out.println(student);
            entries.add(new SectionEntry(student, scheme));
        }
        entries.sort(Comparator.comparing(SectionEntry::isFrozen));

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

        this.setRowFactory(tv -> {
            TableRow<SectionEntry> row = new TableRow<>();
            final BooleanProperty isFrozen = new SimpleBooleanProperty(true);
            final BooleanBinding frozen = row.itemProperty().isNotNull().and(
                    Bindings.selectBoolean(row.itemProperty(), "frozen"));
            row.disableProperty().bind(frozen);
            row.styleProperty().bind(Bindings
                    .when(frozen.isEqualTo(isFrozen))
                    .then("-fx-background-color: darkgrey")
                    .otherwise(""));
            return row;
        });

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
