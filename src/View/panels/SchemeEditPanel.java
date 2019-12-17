package View.panels;

import Model.Subject;
import Controller.SchemeEditPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SchemeEditPanel extends Stage{
	
	public SchemeEditPanel(Subject subject) {
		try {
			SchemeEditPanelController controller = new SchemeEditPanelController(subject);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SchemeEditPanel.fxml"));
			loader.setController(controller);
			
			Parent root = loader.load();

			Scene scene = new Scene(root);
			setTitle("iGrader");
			setScene(scene);
			show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
