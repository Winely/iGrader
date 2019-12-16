package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Database.DAO;
import Model.*;
import View.panels.SectionTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import sample.Main;


public class EditAssignmentGradesController {


    @FXML
    private TableColumn<Record, String> nameColumn;
    @FXML
    private TableColumn<Record, String> gradeColumn;
    @FXML
    private TableColumn<Record, String> bonusColumn;
    @FXML
    private TableView<Record> recordTable;


    @FXML
    private void onEditPoints(CellEditEvent<Record,String> pointsCellEdited) {
        Record record = recordTable.getSelectionModel().getSelectedItem();
        record.setPoints(Double.parseDouble(pointsCellEdited.getNewValue()));
    }
    @FXML
    private void onEditBonus(CellEditEvent<Record,String> bonusCellEdited){
        Record record = recordTable.getSelectionModel().getSelectedItem();
        record.setBonus(Double.parseDouble(bonusCellEdited.getNewValue()));
    }

    @FXML
    private Label assignmentLabel;
    @FXML
    private Label maxscoreLabel;
    @FXML
    private TextArea commentsArea;
    private int gradeWay = 1;

    private Stage dialogStage;
    private Subject subject;
    private ObservableList<Record> recordData = FXCollections.observableArrayList();;
    private ObservableList<Record> realData = FXCollections.observableArrayList();;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("name"));

        gradeColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("points"));
        gradeColumn.setCellFactory(TextFieldTableCell.<Record>forTableColumn());

        bonusColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("bonus"));
        bonusColumn.setCellFactory(TextFieldTableCell.<Record>forTableColumn());

        recordTable.setEditable(true);

    }

    @FXML
    private void percentWay() {
        gradeWay = -1;
        int n = 0;
        recordData = recordTable.getItems();
        for(Record i : realData) {
            recordData.get(n).setPoints(i.getGrade().getScore().getPoint()/subject.getMaxScore().getPoint());
            if(subject.getMaxScore().getBonus() != 0)//otherwise 0/0 =nan
                recordData.get(n).setBonus(i.getGrade().getScore().getBonus()/subject.getMaxScore().getBonus());
            else
                recordData.get(n).setBonus(0);
            n++;
        }
    }
    @FXML
    private void lostWay() {
        gradeWay = 0;
        int n = 0;
        recordData = recordTable.getItems();
        for(Record i : realData) {
            recordData.get(n).setPoints(i.getGrade().getScore().getPoint() - subject.getMaxScore().getPoint());
            recordData.get(n).setBonus(i.getGrade().getScore().getBonus() - subject.getMaxScore().getBonus());
            n++;
        }
    }
    @FXML
    private void rawWay() {
        gradeWay = 1;
        int n = 0;
        recordData = recordTable.getItems();
        for(Record i : realData) {
            recordData.get(n).setPoints(i.getGrade().getScore().getPoint());
            recordData.get(n).setBonus(i.getGrade().getScore().getBonus());
            n++;
        }
    }
    @FXML
    private void pressBack() {
        Main.handle(Main.UPDATE);
    }

    @FXML
    private void handleSave() {
        if(isInputValid()) {
            if(gradeWay == -1) {
                for(Record i:recordTable.getItems()) {
                	if(subject.getGrades().get(i.getStudent()) == null) {
                		i.getGrade().setScore(new Score(subject.getMaxScore().getPoint() * i.getGrade().getScore().getPoint(),subject.getMaxScore().getBonus() * i.getGrade().getScore().getBonus()));
                		subject.getGrades().put(i.getStudent(), i.getGrade());
                		 subject.getGrades().get(i.getStudent()).save();
                	}else {
                    subject.getGrades().get(i.getStudent()).setScore(new Score(subject.getMaxScore().getPoint() * i.getGrade().getScore().getPoint(),subject.getMaxScore().getBonus() * i.getGrade().getScore().getBonus()));
                    subject.getGrades().get(i.getStudent()).update();
                    }
                }
            } else if(gradeWay == 0)
            {
                for(Record i:recordTable.getItems()) {
                	if(subject.getGrades().get(i.getStudent()) == null) {
                		i.getGrade().setScore(new Score(subject.getMaxScore().getPoint() + i.getGrade().getScore().getPoint(),subject.getMaxScore().getBonus() + i.getGrade().getScore().getBonus()));
                		subject.getGrades().put(i.getStudent(), i.getGrade());
                		 subject.getGrades().get(i.getStudent()).save();
                	}else {
                    subject.getGrades().get(i.getStudent()).setScore(new Score(subject.getMaxScore().getPoint() + i.getGrade().getScore().getPoint(),subject.getMaxScore().getBonus() + i.getGrade().getScore().getBonus()));
                    subject.getGrades().get(i.getStudent()).update();
                	}
                }
            } else {
                for(Record i:recordTable.getItems()) {
                	if(subject.getGrades().get(i.getStudent()) == null) {
                		subject.getGrades().put(i.getStudent(), i.getGrade());
                		 subject.getGrades().get(i.getStudent()).save();
                	}else {
                		subject.getGrades().get(i.getStudent()).setScore(i.getGrade().getScore());
                        subject.getGrades().get(i.getStudent()).update();
                	}
                }
            }
        }
        Map<Student, Grade> record = subject.getGrades();
        realData.clear();
        for(Map.Entry<Student, Grade> entry : record.entrySet()) {
            realData.add(new Record(entry.getKey(),entry.getValue()));
        }
        subject.setComment(commentsArea.getText());
        subject.update();
        Main.handle(Main.UPDATE);

    }

    public void setSubject(int subjectID, Section section) {
        subject = new DAO().findById(Subject.class, subjectID);
        List<Student> included = new ArrayList<Student>();
        System.out.println(subject.getGrades());
        Map<Student, Grade> record = subject.getGrades();
        for(Map.Entry<Student, Grade> entry : record.entrySet()) {
        	if(!entry.getKey().isFrozen()) {
            realData.add(new Record(entry.getKey(),entry.getValue()));
            included.add(entry.getKey());
        	}
        }
        for(Student i: section.getStudents()) {
        	if(!included.contains(i))
        	{
        		if(!i.isFrozen()) {
        		realData.add(new Record(i, new Grade(i,subject,Score.ZERO)));
        		}
        	}
        }
        for(Record i : realData)
        {
            Record showRecord = new Record(i.getStudent(), i.getGrade());//deep clone
            recordData.add(showRecord);
        }

        recordTable.setItems(recordData);
        showStudentInformation();

    }

    public void showStudentInformation() {
        assignmentLabel.setText(subject.getLabel());
        maxscoreLabel.setText(String.valueOf(subject.getMaxScore().getPoint()));
        commentsArea.setText(subject.getComment());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if(gradeWay == -1) {
            for(Record i:recordTable.getItems()) {
                if(!(i.getGrade().getScore().getPoint()>=0&&i.getGrade().getScore().getPoint()<=1&&i.getGrade().getScore().getBonus()>=0&&i.getGrade().getScore().getBonus()<=1))
                    errorMessage += "No valid percentage. Use the format 0.xx in proper range\n";
            }
        } else if(gradeWay == 0)
        {
            for(Record i:recordTable.getItems()) {
                if(!(i.getGrade().getScore().getPoint()<=0&&i.getGrade().getScore().getPoint()>=-(subject.getMaxScore().getPoint())&&i.getGrade().getScore().getBonus()<=0&&i.getGrade().getScore().getBonus()>=-(subject.getMaxScore().getBonus())))
                    errorMessage += "No valid percentage. Use the format -xx points in proper range.\n";
            }
        } else {
            for(Record i:recordTable.getItems()) {
                if(!(i.getGrade().getScore().getPoint()>=0&&i.getGrade().getScore().getPoint()<=subject.getMaxScore().getPoint()&&i.getGrade().getScore().getBonus()>=0&&i.getGrade().getScore().getBonus()<=subject.getMaxScore().getBonus()))
                    errorMessage += "No valid percentage. Use the format xx points in proper range.\n";
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



    public class Record {
        private Student student;
        private Grade grade;
        private StringProperty name ;
        private StringProperty points ;
        private StringProperty bonus = new SimpleStringProperty();
        public Record(Student student, Grade grade) {//questions about point, deep copy or just copy reference.
            this.student = student;
            this.grade = new Grade(student, subject, new Score(grade.getScore().getPoint(),grade.getScore().getBonus()));
            name = new SimpleStringProperty(student.getName().toString());
            points= new SimpleStringProperty(String.valueOf(grade.getScore().getPoint()));
            bonus= new SimpleStringProperty(String.valueOf(grade.getScore().getBonus()));
        }

        public StringProperty nameProperty() {
            return name;
        }
        public StringProperty pointsProperty() {
            return points;
        }
        public StringProperty bonusProperty() {
            return bonus;
        }
        public void setName(String name) {
            this.name.set(name);
        }
        public void setPoints(double points) {
            this.points.set(String.valueOf(points));
            this.grade.getScore().setPoint(points);
        }
        public void setBonus(double bonus) {
            this.bonus.set(String.valueOf(bonus));
            this.grade.getScore().setBonus(bonus);
        }
        public Student getStudent() {
            return this.student;
        }

        public Grade getGrade() {
            return this.grade;
        }
    }

}
