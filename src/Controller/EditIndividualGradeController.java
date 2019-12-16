package Controller;

import Database.DAO;
import Model.*;
import View.pages.AssignmentChildrenGrades;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Main;

import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;


public class EditIndividualGradeController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label assignmentLabel;
    @FXML
    private TextField gradeField;
    @FXML
    private Label maxGradeLabel;
    @FXML
    private TextField bonuesField;
    @FXML
    private TextArea commentArea;

    private int gradeWay = 1;
    private Subject subject;
    private Section section;
    private Student student;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void percentWay() {
        gradeWay = -1;
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint()/subject.getMaxScore().getPoint()));
        if(subject.getMaxScore().getBonus() != 0) // otherwise /0 = NAN
            bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus()/subject.getMaxScore().getBonus()));
        else
            bonuesField.setText(String.valueOf(0.0));

    }
    @FXML
    private void lostWay() {
        gradeWay = 0;
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint() - subject.getMaxScore().getPoint()));
        bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus() - subject.getMaxScore().getBonus()));

    }

    @FXML
    private void pressBack() {
        navBack(subject, section);
    }

    @FXML
    private void rawWay() {
        gradeWay = 1;
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint()));
        bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus()));

    }
    @FXML
    private void handleSave() {
        if(isInputValid()) {
            if(gradeWay == -1) {
                subject.getGrades().get(student).getScore().setPoint(Double.valueOf(gradeField.getText()) * subject.getMaxScore().getPoint());
                subject.getGrades().get(student).getScore().setBonus(Double.valueOf(bonuesField.getText()) * subject.getMaxScore().getBonus());
            } else if(gradeWay == 0)
            {
                subject.getGrades().get(student).getScore().setPoint(subject.getMaxScore().getPoint() + Double.valueOf(gradeField.getText()));
                subject.getGrades().get(student).getScore().setBonus(subject.getMaxScore().getBonus() + Double.valueOf(bonuesField.getText()));
            } else {
                subject.getGrades().get(student).getScore().setPoint(Double.valueOf(gradeField.getText()));
                subject.getGrades().get(student).getScore().setBonus(Double.valueOf(bonuesField.getText()));
            }
        }
        subject.getGrades().get(student).setComment(commentArea.getText());
        subject.update();
        subject.getGrades().get(student).update();

        navBack(subject, section);
    }

    private static void navBack(Subject subject, Section section) {
        if (subject.getParent().getParent() == null) {
            Main.handle(Main.UPDATE);
        } else {
            Main.window.setScene(new AssignmentChildrenGrades(new BorderPane(), section, subject.getParent()));
        }
    }


    public void setSubject(int subjectID, String studentID) {
        subject = new DAO().findById(Subject.class, subjectID);
        Map<Student, Grade> map= subject.getGrades();
        for(Map.Entry<Student, Grade> a:map.entrySet()){
            if(a.getKey().getId().equals(studentID))
                student = a.getKey();
        }
        showStudentInformation();
    }

    public void setSection(Section section) {
        this.section = section;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if(gradeWay == -1) {
            if (gradeField.getText() == null || Double.valueOf(gradeField.getText())<0 || Double.valueOf(gradeField.getText()) > 1) {
                errorMessage += "No valid percentage!\n";
            }
            if (bonuesField.getText() == null || Double.valueOf(bonuesField.getText())<0 || Double.valueOf(bonuesField.getText()) > 1) {
                errorMessage += "No valid percentage!\n";
            }
        } else if(gradeWay == 0)
        {
            if (gradeField.getText() == null || Double.valueOf(gradeField.getText())>0 || Double.valueOf(gradeField.getText()) < -(subject.getMaxScore().getPoint())) {
                errorMessage += "No valid lost points!\n";
            }
            if (bonuesField.getText() == null || Double.valueOf(bonuesField.getText())>0 || Double.valueOf(bonuesField.getText()) < -(subject.getMaxScore().getBonus())) {
                errorMessage += "No valid lost points!\n";
            }
        } else {
            if (gradeField.getText() == null || Double.valueOf(gradeField.getText())<0 || Double.valueOf(gradeField.getText()) > subject.getMaxScore().getPoint()) {
                errorMessage += "No valid points!\n";
            }
            if (bonuesField.getText() == null || Double.valueOf(bonuesField.getText())<0 || Double.valueOf(bonuesField.getText()) > subject.getMaxScore().getBonus()) {
                errorMessage += "No valid points!\n";
            }
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

    public void showStudentInformation() {
        nameLabel.setText(student.getName().toString());
        assignmentLabel.setText(subject.getLabel());
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint()));
        maxGradeLabel.setText(String.valueOf(subject.getMaxScore().getPoint()));
        bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus()));
        commentArea.setText(subject.getGrades().get(student).getComment());
    }
}
