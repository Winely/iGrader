package Panels;

import Model.Section;
import Model.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.*;

public class StatisticsPanel extends Stage{

	public StatisticsPanel(Section section) {
		try {
			StatisticsPanelController controller = new StatisticsPanelController(section);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsPanel.fxml"));
			loader.setController(controller);
			
			Parent root = loader.load();

			Scene scene = new Scene(root);
			setTitle("Class Statistics");
			setScene(scene);
			show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
