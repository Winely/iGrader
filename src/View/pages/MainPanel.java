package View.pages;

import Model.*;
import Model.Class;
import View.components.ClassSelect;
import View.panels.SectionTabContent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.Main;


public class MainPanel extends Scene implements EventHandler<ActionEvent> {

    private ToolBar toolbar = new ToolBar();
    private TabPane tabPane = new TabPane();
    private ClassSelect classSelect = new ClassSelect();
    private Button newClass = new Button("New Class");
    private Button settings = new Button("Settings");

    private Button edit = new Button("Edit Scheme");
    private Button removeClass = new Button("Remove Class");

    public MainPanel(BorderPane layout) {
        super(layout, 800, 800);

        setupToolbar();
        setupTabs();
//        setupTabs(Main.classes.get(0));
        ToolBar bottom = createClassToolbar();

        newClass.setOnAction(this);
        settings.setOnAction(this);
        edit.setOnAction(this);
        removeClass.setOnAction(this);

        layout.setTop(toolbar);
        layout.setCenter(tabPane);
        layout.setBottom(bottom);

    }

    private void setupToolbar() {
        toolbar.getItems().addAll(classSelect, newClass, settings);
    }


    private void setupTabs() {
        Class c = new Class("Test");
        Section section1 = new Section("A1");
        Section section2 = new Section("A2");
        Subject rando = new Subject("Rando", 1.0);
        section1.addChild(rando);
        c.addSection(section1);
        c.addSection(section2);
        for (int i = 0; i < c.getSections().size(); i++) {
            Section section = c.getSections().get(i);
            Tab tabI = new Tab(section.getLabel());
            tabI.setContent(new SectionTabContent(section));
            tabPane.getTabs().add(tabI);
        }
    }


    private ToolBar createClassToolbar() {
        ToolBar bottom = new ToolBar();
        bottom.getItems().addAll(edit, removeClass);
        return bottom;
    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == newClass){
            System.out.println("newClass");
        } else if (event.getSource() == settings){
            System.out.println("settings");
        } else if (event.getSource() == removeClass){
            System.out.println("removeClass");
        }

    }



}

























