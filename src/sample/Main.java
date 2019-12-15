package sample;

import Database.DAO;
import Model.Course;
import Model.Subject;
import View.pages.LoginPanel;
import View.pages.MainPanel;
import View.pages.NewCourse;
import View.pages.SettingsPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;

import java.util.Scanner;

public class Main extends Application {

    private static LoginPanel loginPanel = new LoginPanel(new GridPane());
    private static MainPanel mainPanel = new MainPanel(new BorderPane());
    private static SettingsPanel settingsPanel  = new SettingsPanel(new GridPane());
    private static NewCourse newCourse  = new NewCourse(new GridPane());
    public static final String LOGIN = "Log In";
    public static final String SETTINGS = "Settings";
    public static final String NEW_COURSE = "New Course";
    public static final String UPDATE = "Update";
    public static final String EDIT = "Edit";
    public static Course EMPTY = new Course();
    public static Subject EMPTY_SUB = new Subject();

    public static String passcode;
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader.load(getClass().getResource("sample.fxml"));
        // setup passcode
        File file = new File("src/config.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
            passcode = sc.nextLine();

        // setup empty class (just a dummy class for list options
        EMPTY_SUB.setLabel("New Template");
        EMPTY.setScheme(EMPTY_SUB);

        window = primaryStage;
        primaryStage.setTitle("iGrader");
        primaryStage.setScene(loginPanel);
        primaryStage.show();
    }

    public static void handle(String description) {
        switch (description) {
            case LOGIN:
                mainPanel = new MainPanel(new BorderPane());
                window.setScene(mainPanel);
                break;
            case SETTINGS:
                window.setScene(settingsPanel);
                break;
            case NEW_COURSE:
                window.setScene(newCourse);
                break;
            case UPDATE:
                mainPanel.setupTabs();
                window.setScene(mainPanel);
                break;
        }
    }

    public static void courseAdded(Course course){
        handle(LOGIN);
        mainPanel.selectClass(course);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
