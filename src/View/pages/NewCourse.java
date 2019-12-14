package View.pages;

import Controller.CourseController;
import Model.Course;
import Model.Subject;
import View.components.ClassSelect;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.Main;

public class NewCourse extends Scene {
    private CourseController courseController = new CourseController();

    private TextField courseName = new TextField("");
    private Label warning = new Label();
    private ClassSelect classSelect = new ClassSelect();

    public NewCourse(GridPane layout) {
        super(layout, 500, 250);
        Label header = new Label("Create a New Class");
        Button confirm = new Button("Confirm");
        Button back = new Button("Back");
        confirm.setOnAction(this::pressConfirm);
        back.setOnAction(this::pressBack);
        layout.add(header, 10, 3);

        Label nameLabel = new Label("Class Name: ");
        Label templateLabel = new Label("Select Template: ");

        layout.add(nameLabel, 5, 5);
        layout.add(courseName, 10, 5);
        layout.add(templateLabel, 5, 10);
        layout.add(classSelect, 10, 10);
        layout.add(back, 5, 12);
        layout.add(confirm, 10, 12);
        layout.add(warning, 5, 17);

        layout.setHgap(10);
        layout.setVgap(10);
    }

    private void pressConfirm(ActionEvent event) {
        Course selected = classSelect.getValue();
        String name = courseName.getText();
        if (selected == null) {
            courseController.createNewCourse(name, new Subject());
            Main.handle(Main.LOGIN);
        } else {
            courseController.createNewCourse(name, selected.getScheme());
            Main.handle(Main.LOGIN);
        }
    }

    private void pressBack(ActionEvent event) {
        Main.handle(Main.LOGIN);
    }

}
