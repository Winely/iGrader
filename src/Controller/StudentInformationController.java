package Controller;

import Model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import View.Main;

public class StudentInformationController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private TextArea commentArea;

    private Stage dialogStage;
    private Student student;
    private String id;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    @FXML
    private void pressBack() {
        Main.handle(Main.UPDATE);
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setStudentId(Student student) {
        this.student = student;
        showStudentInformation();
    }
    public void showStudentInformation() {
        nameLabel.setText(student.getName().getFirstName()+student.getName().getMidName()+student.getName().getLastName());
        idLabel.setText(student.getId());
        commentArea.setText(student.getComment());
    }

    @FXML
    private void handleOk() {
        student.setComment(commentArea.getText());
        student.update();
        Main.handle(Main.UPDATE);
    }

}
