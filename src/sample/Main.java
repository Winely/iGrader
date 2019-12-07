package sample;

import Model.Class;
import View.pages.LoginPanel;
import View.pages.MainPanel;
import View.pages.SettingsPanel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application implements EventHandler<ActionEvent> {

    private LoginPanel loginPanel = new LoginPanel(new GridPane());
    private MainPanel mainPanel = new MainPanel(new BorderPane());
    private SettingsPanel settingsPanel  = new SettingsPanel(new GridPane());
    public static final String LOGIN = "Log In";
    public static final String LOGOUT = "Log Out";
    public static ArrayList<Class> classes = new ArrayList<>() {
        {add(new Class("Dummy"));}
    };

    public static String passcode = "admin";

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        primaryStage.setTitle("iGrader");
//        primaryStage.setScene(loginPanel);
//        primaryStage.setScene(settingsPanel);
        primaryStage.setScene(mainPanel);
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        switch (((Button)event.getSource()).getText()) {
            case LOGIN:
                window.setScene(mainPanel);
        }
    }

    public void newScene(Scene scene) {
        window.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
