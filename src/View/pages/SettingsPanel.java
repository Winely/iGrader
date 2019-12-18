package View.pages;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import View.Main;

public class SettingsPanel extends Scene {

    private PasswordField passcode1 = new PasswordField();
    private PasswordField passcode2 = new PasswordField();
    private Label warning = new Label();

    public SettingsPanel(GridPane layout) {
        super(layout, 500, 300);
        Label oldP = new Label("Please enter your old passcode:");
        Label newP = new Label("Please enter your new passcode:");

        Button confirm = new Button("Confirm");
        Button back = new Button("Back");
        confirm.setOnAction(this::pressConfirm);
        back.setOnAction(this::pressBack);

        layout.add(oldP, 5, 5);
        layout.add(passcode1, 10, 5);
        layout.add(newP, 5, 10);
        layout.add(passcode2, 10, 10);
        layout.add(back, 5, 15);
        layout.add(confirm, 10, 15);
        layout.add(warning, 5, 20);

        layout.setHgap(10);
        layout.setVgap(10);
    }


    private void pressConfirm(ActionEvent event) {
        if (this.passcode1.getText().equals(Main.passcode)) {
            Main.passcode = passcode2.getText();
            Main.handle(Main.LOGIN);
        } else {
            warning.setText("You did not enter your old passcode correctly");
        }
    }

    private void pressBack(ActionEvent event) {
        Main.handle(Main.LOGIN);
    }
}
