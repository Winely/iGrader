package Controller;

import Model.Name;
import Model.Section;
import Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

public class AddStudentController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField midNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField idField;

    @FXML
    private Stage dialogStage;
    private Student student;
    private boolean isGra = false;
    private Section section;


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSection(Section section) {
        this.section = section;
    }


    @FXML
    private void handleOk() {
        if(isInputValid()) {
            student = new Student(idField.getText(),new Name(firstNameField.getText(),midNameField.getText(),lastNameField.getText()),isGra);
            student.setSection(section);
            student.save();
            Main.handle(Main.LOGIN);
        }
    }
    @FXML
    private void handleGra() {
        isGra = true;
    }
    @FXML
    private void handleUngra() {
        isGra = false;
    }
    @FXML
    private void pressBack() {
        Main.handle(Main.LOGIN);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (midNameField.getText() == null || midNameField.getText().length() == 0) {
            errorMessage += "No valid mid name!\n";
        }
        if (idField.getText() == null || idField.getText().length() == 0) {
            errorMessage += "No valid id!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
