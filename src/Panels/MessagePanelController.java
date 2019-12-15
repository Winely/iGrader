package Panels;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.persistence.*;
import javax.persistence.*;

public class MessagePanelController implements Initializable{

	String message;
	
	public MessagePanelController(String message) {
		this.message = message;
	}

	@FXML private Label textLabel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textLabel.setText(message);
	}
}
