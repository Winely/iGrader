package sample;

import View.pages.LoginPanel;
import View.pages.MainPanel;
import View.pages.NewCourse;
import View.pages.SettingsPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    private static LoginPanel loginPanel = new LoginPanel(new GridPane());
    private static MainPanel mainPanel = new MainPanel(new BorderPane());
    private static SettingsPanel settingsPanel  = new SettingsPanel(new GridPane());
    private static NewCourse newCourse  = new NewCourse(new GridPane());
    public static final String LOGIN = "Log In";
    public static final String SETTINGS = "Settings";
    public static final String NEW_COURSE = "New Course";

    public static String passcode;
    private static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // setup passcode
        File file = new File("src/config.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
            passcode = sc.nextLine();

        window = primaryStage;
        primaryStage.setTitle("iGrader");
        primaryStage.setScene(loginPanel);
        primaryStage.show();
    }

    public static void handle(String description) {
        switch (description) {
            case LOGIN:
                window.setScene(new MainPanel(new BorderPane()));
                break;
            case SETTINGS:
                window.setScene(settingsPanel);
                break;
            case NEW_COURSE:
                window.setScene(newCourse);
                break;

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
