package Controller;

import Model.*;
import View.pages.AssignmentChildrenGrades;
import Model.Grade;
import Model.Score;
import Model.Student;
import Model.Subject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import View.Main;


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
    	if(subject.getGrades().containsKey(student))
    	{
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint()/subject.getMaxScore().getPoint()));
        if(subject.getMaxScore().getBonus() != 0) // otherwise /0 = NAN
            bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus()/subject.getMaxScore().getBonus()));
        else
            bonuesField.setText(String.valueOf(0.0));
    	}else {
    		gradeField.setText(String.valueOf(0));
                bonuesField.setText(String.valueOf(0.0));	
    	}
    }
    @FXML
    private void lostWay() {
        gradeWay = 0;
    	if(subject.getGrades().containsKey(student))
    	{
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint() - subject.getMaxScore().getPoint()));
        bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus() - subject.getMaxScore().getBonus()));
    	}else {
            gradeField.setText(String.valueOf(0 - subject.getMaxScore().getPoint()));
            bonuesField.setText(String.valueOf(0 - subject.getMaxScore().getBonus()));
    	}
    }

    @FXML
    private void pressBack() {
        navBack(subject, section);
    }

    @FXML
    private void rawWay() {
        gradeWay = 1;
    	if(subject.getGrades().containsKey(student))
    	{
        gradeField.setText(String.valueOf(subject.getGrades().get(student).getScore().getPoint()));
        bonuesField.setText(String.valueOf(subject.getGrades().get(student).getScore().getBonus()));
    	}else {
    		gradeField.setText("0");
            bonuesField.setText("0");
    	}
    }
    @FXML
    private void handleSave() {
        boolean isNew = !subject.getGrades().containsKey(student);
        Grade grade = isNew ? new Grade(student, subject, new Score(0, 0)) : subject.getGrades().get(student);
        if(isInputValid()) {
            double point = Double.parseDouble(gradeField.getText());
            double bonus = Double.parseDouble(bonuesField.getText());
            if(gradeWay == -1) {
                grade.getScore().setPoint(point * subject.getMaxScore().getPoint());
                grade.getScore().setBonus(bonus * subject.getMaxScore().getBonus());
            } else if(gradeWay == 0)
            {
                grade.getScore().setPoint(subject.getMaxScore().getPoint() + point);
                grade.getScore().setBonus(subject.getMaxScore().getBonus() + bonus);
            } else {
                grade.getScore().setPoint(point);
                grade.getScore().setBonus(bonus);
            }
        }
        grade.setComment(commentArea.getText());
        if (isNew) grade.save();
        else grade.update();
        subject.refresh();
        Main.handle(Main.UPDATE);

        navBack(subject, section);
    }

    private static void navBack(Subject subject, Section section) {
        if (subject.getParent().getParent() == null) {
            Main.handle(Main.UPDATE);
        } else {
            Main.window.setScene(new AssignmentChildrenGrades(new BorderPane(), section, subject.getParent()));
        }
    }

    public void setSubject(Subject subject, Student student) {
        this.subject = subject;
        this.student = student;
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
        Score score = subject.getScoreByStudent(student);
        gradeField.setText(String.valueOf(score.getPoint()));
        maxGradeLabel.setText(String.valueOf(subject.getMaxScore().getPoint()));
        bonuesField.setText(String.valueOf(score.getBonus()));
        commentArea.setText(subject.getGrades().containsKey(student) ? subject.getGrades().get(student).getComment() : "");
    }
}
