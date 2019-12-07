package View.panels;

import Model.Section;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class SectionTabContent extends BorderPane implements EventHandler<ActionEvent> {

    private Section section;

    private Button addStudent = new Button("+");
    private Button removeStudent = new Button("-");
    private Button comment = new Button("Student Comment");
    private Button withdraw = new Button("Withdraw");
    private Button importBtn = new Button("Import CSV");
    private Button stats = new Button("Statistics");

    public SectionTabContent(Section section) {
        this.section = section;
        addStudent.setOnAction(this);
        removeStudent.setOnAction(this);
        comment.setOnAction(this);
        withdraw.setOnAction(this);
        importBtn.setOnAction(this);
        stats.setOnAction(this);
        setupTabContent();
    }


    private void setupTabContent() {
        SubjectTable sectionTable = new SubjectTable(section);
        ToolBar studentToolbar = createStudentToolbar();
        setCenter(sectionTable);
        setBottom(studentToolbar);
    }

    private ToolBar createStudentToolbar() {
        ToolBar students = new ToolBar();
        students.getItems().addAll(addStudent, removeStudent, comment, withdraw, importBtn, stats);
        return students;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == addStudent) {
            System.out.println("addStudent");
        } else if (event.getSource() == removeStudent) {
            System.out.println("removeStudent");
        } else if (event.getSource() == comment) {
            System.out.println("comment");
        } else if (event.getSource() == withdraw) {
            System.out.println("withdraw");
        } else if (event.getSource() == importBtn) {
            System.out.println("importBtn");
        } else if (event.getSource() == stats) {
            System.out.println("stats");
        }
    }
}
