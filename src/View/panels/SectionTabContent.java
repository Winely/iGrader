package View.panels;

import Controller.ImportStudentController;
import Database.DAO;
import Model.Section;
import Model.Student;
import Panels.StatisticsPanel;
import View.pages.MainPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import sample.Main;

import java.io.File;


public class SectionTabContent extends BorderPane implements EventHandler<ActionEvent> {

    private Section section;

    private Button addStudent = new Button("+");
    private Button removeStudent = new Button("-");
    private Button comment = new Button("Student Comment");
    private Button withdraw = new Button("Withdraw");
    private Button importBtn = new Button("Import CSV");
    private Button stats = new Button("Statistics");
    private static FileChooser fileChooser = new FileChooser();

    private SectionTable sectionTable;

    public SectionTabContent(Section section) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        this.section = section;
        addStudent.setOnAction(this);
        removeStudent.setOnAction(this);
        comment.setOnAction(this);
        withdraw.setOnAction(this);
        importBtn.setOnAction(this);
        stats.setOnAction(this);
        sectionTable = new SectionTable(this.section);
        setupTabContent();
    }


    private void setupTabContent() {
        ToolBar studentToolbar = createStudentToolbar();
        setCenter(sectionTable);
        setBottom(studentToolbar);
    }

    private ToolBar createStudentToolbar() {
        ToolBar students = new ToolBar();
        students.getItems().addAll(addStudent, removeStudent, comment, withdraw, importBtn, stats);
        return students;
    }

    private void removeStudent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to remove this student? All data on this student will be lost");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Student toRemove = sectionTable.getSelectionModel().getSelectedItem().studentProperty().getValue();
            new DAO().delete(Student.class, toRemove.getId());
            section.refresh();
        }
    }

    private void withdrawStudent() {
        Student toWithdraw = sectionTable.getSelectionModel().getSelectedItem().studentProperty().getValue();
        toWithdraw.setFrozen(true);
        toWithdraw.update();
    }

    private static void importStudents(Section section) {
        File selectedFile = fileChooser.showOpenDialog(Main.window);
        String path = selectedFile.getAbsolutePath();
        ImportStudentController.importFromCSV(path, section);
        section.refresh();
    }

    private static void openStats(Section section) {
        new StatisticsPanel(section);
    }


    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == addStudent) {
            System.out.println("addStudent");
        } else if (event.getSource() == removeStudent) {
            removeStudent();
            Main.handle(Main.UPDATE);
        } else if (event.getSource() == comment) {
            System.out.println("comment");
        } else if (event.getSource() == withdraw) {
            withdrawStudent();
            Main.handle(Main.UPDATE);
        } else if (event.getSource() == importBtn) {
            importStudents(section);
            Main.handle(Main.UPDATE);
        } else if (event.getSource() == stats) {
            openStats(section);
            System.out.println("stats");
        }
    }
}
