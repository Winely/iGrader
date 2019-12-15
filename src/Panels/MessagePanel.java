package Panels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.*;

public class MessagePanel {

	public MessagePanel(String message) {
		try {			
			Stage stage = new Stage();
			MessagePanelController controller = new MessagePanelController(message);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MessagePanel.fxml"));
			loader.setController(controller);
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root);
			stage.setTitle("iGrader");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
