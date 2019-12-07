package View.pages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends Scene {

    public SettingsPanel(GridPane layout) {
        super(layout, 500, 250);
        Label oldP = new Label("Please enter your old passcode:");
        Label newP = new Label("Please enter your new passcode:");
        TextField passcode1 = new TextField();
        TextField passcode2 = new TextField();
        Button confirm = new Button("Confirm");
        layout.add(oldP, 5, 5);
        layout.add(passcode1, 10, 5);
        layout.add(newP, 5, 10);
        layout.add(passcode2, 10, 10);
        layout.add(confirm, 5, 15);

        layout.setHgap(10);
        layout.setVgap(10);
    }
}
