package View.pages;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import View.Main;

public class LoginPanel extends Scene {

    private PasswordField passcode = new PasswordField();
    private Label warning = new Label();

    public LoginPanel(GridPane layout) {
        super(layout, 500, 250);
        Label header = new Label("Welcome to iGrader! Please input your passcode:");
        Button login = new Button("Log In");
        login.setOnAction(this::pressLogin);

        layout.add(header, 10, 5);
        layout.add(passcode, 10, 6);
        layout.add(login, 10, 7);
        layout.add(warning, 10, 9);

        layout.setHgap(10);
        layout.setVgap(10);
    }

    private void pressLogin(ActionEvent event) {
        if (this.passcode.getText().equals(Main.passcode)) {
            Main.handle(Main.LOGIN);
        } else {
            warning.setText("Incorrect passcode, try again");
        }
    }

}
