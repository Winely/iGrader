package Panels;

import Model.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.*;

public class SchemeEditPanel{
	
	public SchemeEditPanel(Subject subject) {
		try {			
			Stage stage = new Stage();
			SchemeEditPanelController controller = new SchemeEditPanelController(subject);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SchemeEditPanel.fxml"));
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