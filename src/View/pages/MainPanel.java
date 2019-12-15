package View.pages;

import Controller.CourseController;
import Model.*;
import Model.Course;
import Panels.SchemeEditPanel;
import Panels.StatisticsPanel;
import View.components.ClassSelect;
import View.panels.SectionTabContent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.Main;


public class MainPanel extends Scene implements EventHandler<ActionEvent>{

    private CourseController courseController = new CourseController();

    private ToolBar toolbar = new ToolBar();
    private TabPane tabPane = new TabPane();
    private ClassSelect classSelect = new ClassSelect();
    private Button newCourse = new Button("New Class");
    private Button settings = new Button("Settings");

    private Button edit = new Button("Edit Scheme");
    private Button removeClass = new Button("Remove Class");
    private Button addSection = new Button("Add Section");

    public MainPanel(BorderPane layout) {
        super(layout, 800, 800);

        setupToolbar();
        setupTabs();
        ToolBar bottom = createClassToolbar();

        newCourse.setOnAction(this);
        settings.setOnAction(this);
        edit.setOnAction(this);
        removeClass.setOnAction(this);
        addSection.setOnAction(this);
        classSelect.setOnAction(this);

        layout.setTop(toolbar);
        layout.setCenter(tabPane);
        layout.setBottom(bottom);

    }

    private void setupToolbar() {
        toolbar.getItems().addAll(classSelect, newCourse, settings);
    }


    public void setupTabs() {
        Course c = classSelect.getValue();
        tabPane.getTabs().clear();
        if (c != null) {
            for (int i = 0; i < c.getSections().size(); i++) {
                Section section = c.getSections().get(i);
                Tab tab = new Tab(section.getLabel());
                tab.setContent(new SectionTabContent(section));
                tab.setOnCloseRequest(new RemoveSectionHandler(section));
                tabPane.getTabs().add(tab);
            }
        }
    }

    private ToolBar createClassToolbar() {
        if (classSelect.getValue() != null) {
            return null;
        }
        ToolBar bottom = new ToolBar();
        bottom.getItems().addAll(edit, removeClass, addSection);
        return bottom;
    }

    private static void openEditPanel(Subject subject) {
        new SchemeEditPanel(subject);
    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == newCourse) {
            Main.handle(Main.NEW_COURSE);
        } else if (event.getSource() == settings) {
            Main.handle(Main.SETTINGS);
        } else if (event.getSource() == removeClass) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("All students and grades under this course will be removed. Are you sure?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                CourseController.removeCourse(classSelect.getValue());
            } else {
                event.consume();
            }
        } else if (event.getSource() == addSection) {
            Course selected = classSelect.getValue();
            courseController.addSection(selected);
            setupTabs();
        } else if (event.getSource() == classSelect) {
            setupTabs();
        } else if (event.getSource() == edit) {
            Subject scheme = classSelect.getValue().getScheme();
            openEditPanel(scheme);
        }
    }

}

class RemoveSectionHandler implements EventHandler<Event> {
    private Section section;
    public RemoveSectionHandler() {
    }

    public RemoveSectionHandler(Section section) {
        this.section = section;
    }

    @Override
    public void handle(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All students and grades under this section will be removed. Are you sure?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            CourseController.removeSection(section);
        } else {
            event.consume();
        }
    }
}
