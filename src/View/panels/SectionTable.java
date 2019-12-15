package View.panels;
import Model.*;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
        TableColumn<SectionEntry, Student> nameCol = new TableColumn<>("Names");
        nameCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().studentProperty().getValue()));

        nameCol.setCellFactory(column -> {
            return new TableCell<SectionEntry, Student>() {
                @Override
                protected void updateItem(Student item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (item == null) {
                        return;
                    }
                    setText( item.toString() );
                    setTooltip(new Tooltip(item.getComment()));
                }
            };
        });

        columns.add(nameCol);

        // create columns for every assignment that is part of this subject
        for (int i = 0; i < scheme.getChildren().size(); i++) {
            Subject sub = scheme.getChildren().get(i);
            String label = sub.getLabel();
            System.out.println(label);
            TableColumn<SectionEntry, Grade> subCol = new TableColumn<>(label);
            subCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().scoreMap.get(label)));
            subCol.setSortable(false);

            makeHeader(subCol, label, i);

            EventHandler<? super MouseEvent> handler = event -> {
                System.out.println("Column clicked " + ((Node)event.getTarget()).getProperties().get("index"));
            };
            subCol.getGraphic().addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

            subCol.setCellFactory(column -> {
                return new TableCell<SectionEntry, Grade>() {
                    @Override
                    protected void updateItem(Grade item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (item == null) {
                            return;
                        }
                        setText( item.toString() );
                        setTooltip(new Tooltip(item.getComment()));
                    }
                };
            });

            columns.add(subCol);
        }

        TableColumn<SectionEntry, Double> finalGrade = new TableColumn<>("Final Grade");
        finalGrade.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().scheme.getFinalScoreByStudent(param.getValue().getStudent()).getPoint()));

        TableColumn<SectionEntry, Double> finalBonus = new TableColumn<>("Final Bonus");
        finalBonus.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().scheme.getFinalScoreByStudent(param.getValue().getStudent()).getBonus()));
        columns.add(finalGrade);
        columns.add(finalBonus);

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

    private void makeHeader(TableColumn<?, ?> target, String name, int index) {
        VBox vBox = new VBox(new Label(name));
        vBox.setAlignment(Pos.CENTER);
        vBox.getProperties().put("index", index);
        target.setGraphic(vBox);
        target.setText("");
    }
}
