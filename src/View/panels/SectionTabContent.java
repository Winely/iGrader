package View.panels;

import Controller.AddStudentController;
import Controller.ImportStudentController;
import Controller.StudentInformationController;
import Database.DAO;
import Model.Section;
import Model.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import sample.Main;

import java.io.File;
import java.io.IOException;


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
        sectionTable = new SectionTable(this.section, this.section.getCourse().getScheme());
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("View/pages/AddStudent.fxml"));
            AnchorPane page;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                page = null;
            }
            Main.setFxmlPane(page);
            Main.handle(Main.NEW_STUDENT);
            AddStudentController controller = loader.getController();
            controller.setSection(section);
            controller.setDialogStage(Main.getStage());
        } else if (event.getSource() == removeStudent) {
            removeStudent();
            Main.handle(Main.UPDATE);
        } else if (event.getSource() == comment) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("View/pages/StudentInformation.fxml"));
            AnchorPane page;
            try {
                page = (AnchorPane) loader.load();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                page = null;
            }
            Main.setFxmlPane(page);
            Main.handle(Main.STUDENT_COMMENT);
            StudentInformationController controller = loader.getController();
            controller.setStudentId(sectionTable.getSelectionModel().getSelectedItem().getStudent());
            controller.setDialogStage(Main.getStage());
        } else if (event.getSource() == withdraw) {
            withdrawStudent();
            Main.handle(Main.UPDATE);
        } else if (event.getSource() == importBtn) {
            importStudents(section);
            Main.handle(Main.UPDATE);
        } else if (event.getSource() == stats) {
            openStats(section);
        }
    }
}
