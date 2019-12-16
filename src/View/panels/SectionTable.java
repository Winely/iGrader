package View.panels;
import Controller.EditAssignmentGradesController;
import Controller.EditIndividualGradeController;
import Database.DAO;
import Model.*;

import View.pages.AssignmentChildrenGrades;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class SectionTable extends TableView<SectionEntry> {

    private Subject scheme;

    public SectionTable(Section section, Subject scheme) {
        super();

        this.scheme = scheme;

        ObservableList<SectionEntry> entries = FXCollections.observableArrayList();

        for (Student student: section.getStudents()) {
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
                    item.refresh();
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
            TableColumn<SectionEntry, Grade> subCol = new TableColumn<>(label);
            subCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().scoreMap.get(label)));

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

            subCol.setSortable(false);
            subCol.setPrefWidth(100.0);

            makeHeader(subCol, label);
            EventHandler<? super MouseEvent> handler = event -> {
                int subId = -1;
                if(event.getTarget().getClass() == LabeledText.class&&((Text)event.getTarget()).getText() != null) {
                    for (Subject subject: scheme.getChildren()) {
                        String sublabel = subject.getLabel();

                        if(sublabel.equals(((Text)event.getTarget()).getText()))
                            subId = subject.getId();
                    }
                    if(subId != -1) {
                        Subject subject = new DAO().findById(Subject.class, subId);
                        if (subject.getChildren().size() > 0) {
                            openAssignmentChildren(section, subject);
                        } else {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getClassLoader().getResource("View/pages/EditAssignmentGrades.fxml"));
                            AnchorPane page;
                            try {
                                page = (AnchorPane) loader.load();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                page = null;
                            }
                            Main.setFxmlPane(page);
                            Main.handle(Main.ASSIGNMENTGRADES);
                            EditAssignmentGradesController controller = loader.getController();
                            controller.setSubject(subId,section);
                            controller.setDialogStage(Main.getStage());
                            controller.setSection(section);
                        }
                    }
                } else if(((Node)event.getTarget()).getProperties().get("Label") != null) {
                    for (Subject subject: scheme.getChildren()) {
                        String sublabel = subject.getLabel();

                        if(sublabel.equals(((Node)event.getTarget()).getProperties().get("Label")))
                            subId = subject.getId();
                    }
                    if(subId != 1) {
                        Subject subject = new DAO().findById(Subject.class, subId);
                        if (subject.getChildren().size() > 0) {
                            openAssignmentChildren(section, subject);
                        } else {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getClassLoader().getResource("View/pages/EditAssignmentGrades.fxml"));
                            AnchorPane page;
                            try {
                                page = (AnchorPane) loader.load();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                page = null;
                            }
                            Main.setFxmlPane(page);
                            Main.handle(Main.ASSIGNMENTGRADES);
                            EditAssignmentGradesController controller = loader.getController();
                            controller.setSubject(subId,section);
                            controller.setDialogStage(Main.getStage());
                            controller.setSection(section);
                        }
                    }
                }
            };
            subCol.getGraphic().addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

            columns.add(subCol);
        }

        TableColumn<SectionEntry, String> finalGrade = new TableColumn<>("Final Grade");
        finalGrade.setCellValueFactory(param -> {
            double score = param.getValue().scheme.getFinalScoreByStudent(param.getValue().getStudent()).getPoint() * 100;
            score = Math.round(score) / 100;
            String scoreStr = Double.toString(score);
            return new ReadOnlyObjectWrapper<>(scoreStr);
        });

        TableColumn<SectionEntry, String> finalBonus = new TableColumn<>("Final Bonus");
        finalBonus.setCellValueFactory(param -> {
            double score = param.getValue().scheme.getFinalScoreByStudent(param.getValue().getStudent()).getBonus() * 100;
            score = Math.round(score) / 100;
            String scoreStr = Double.toString(score);
            return new ReadOnlyObjectWrapper<>(scoreStr);
        });

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
                    Subject subject = scheme.getChildren().get(
                            getSelectionModel().getSelectedCells().get(0).getColumn() - 1
                    );
                    if (subject.getChildren().size() > 0) {
                        openAssignmentChildren(section, subject);
                    } else {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getClassLoader().getResource("View/pages/EditIndividualGrade.fxml"));
                        AnchorPane page;
                        try {
                            page = (AnchorPane) loader.load();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            page = null;
                        }
                        Main.setFxmlPane(page);
                        Main.handle(Main.INDIVIDUALGRADE);
                        EditIndividualGradeController controller = loader.getController();
                        controller.setSubject(subject, getSelectionModel().getSelectedItem().getStudent());
                        controller.setDialogStage(Main.getStage());
                        controller.setSection(section);
                    }
                }
            }
        });
    }

    private void makeHeader(TableColumn<?, ?> target, String name) {
        VBox vBox = new VBox(new Label(name));
        vBox.setAlignment(Pos.CENTER);
        vBox.getProperties().put("Label", name);
        target.setGraphic(vBox);
        target.setText("");
    }

    private static void openAssignmentChildren(Section section, Subject scheme) {
        Main.window.setScene(new AssignmentChildrenGrades(new BorderPane(), section, scheme));
    }
}
