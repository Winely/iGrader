package View.pages;

import Model.Section;
import Model.Subject;
import View.panels.SectionTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Main;

public class AssignmentChildrenGrades extends Scene implements EventHandler<ActionEvent> {

    private Button back = new Button("Back");

    public AssignmentChildrenGrades(BorderPane layout, Section section, Subject scheme) {
        super(layout, 500, 500);
        ToolBar toolbar = new ToolBar();
        back.setOnAction(this);
        SectionTable sectionTable = new SectionTable(section, scheme);
        toolbar.getItems().add(back);
        layout.setCenter(sectionTable);
        layout.setBottom(toolbar);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == back) {
            Main.handle(Main.UPDATE);
        }
    }
}
