package View.pages;

import Controller.CourseController;
import Model.*;
import Model.Course;
import View.components.ClassSelect;
import View.panels.SectionTabContent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.Main;


public class MainPanel extends Scene implements EventHandler<ActionEvent> {

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

        layout.setTop(toolbar);
        layout.setCenter(tabPane);
        layout.setBottom(bottom);

    }

    private void setupToolbar() {
        toolbar.getItems().addAll(classSelect, newCourse, settings);
    }


    private void setupTabs() {
        Course c = classSelect.getValue();
        if (c != null) {
            for (int i = 0; i < c.getSections().size(); i++) {
                Section section = c.getSections().get(i);
                Tab tabI = new Tab(section.getLabel());
                tabI.setContent(new SectionTabContent(section));
                tabPane.getTabs().add(tabI);
            }
        }
    }

//    private void makeDummyData(Course c) {
//        Section section1 = new Section("A1");
//        Section section2 = new Section("A2");
//        Subject scheme = new Subject();
//        Subject hw = new Subject();
//        hw.setLabel("HW");
//        Subject exams = new Subject();
//        exams.setLabel("Exams");
//        scheme.getChildren().add(hw);
//        scheme.getChildren().add(exams);
//        section1.setCourse(c);
//        section2.setCourse(c);
//        c.addSection(section1);
//        c.addSection(section2);
//        c.setScheme(scheme);
//
//        scheme.save();
//        c.save();
//        section1.save();
//        section2.save();
//
//        Name name1 = new Name("Dummy", "Dum", "Dum");
//        Student stu1 = new Student("U12345678", name1, false);
//        Name name2 = new Name("Silly", "Willy", "Student");
//        Student stu2 = new Student("U87654321", name2, false);
//
//        section1.addStudent(stu1);
//        section2.addStudent(stu2);
//
//    }


    private ToolBar createClassToolbar() {
        if (classSelect.getValue() != null) {
            return null;
        }
        ToolBar bottom = new ToolBar();
        bottom.getItems().addAll(edit, removeClass, addSection);
        return bottom;
    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == newCourse) {
            Main.handle(Main.NEW_COURSE);
        } else if (event.getSource() == settings) {
            Main.handle(Main.SETTINGS);
        } else if (event.getSource() == removeClass) {
            System.out.println("removeClass");
        } else if (event.getSource() == addSection) {
            Course selected = classSelect.getValue();
            courseController.addSection(selected);
            Main.handle(Main.LOGIN);
        }

    }



}

























