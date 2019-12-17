package sample;

import Database.DAO;
import Model.Course;
import Model.Section;
import Model.Subject;
import View.panels.SchemeEditPanel;
import View.panels.StatisticsPanel;

public class Controller {
	
	public void openSchemeEditPanel() {
		DAO dao = new DAO();
		
		Course cs591 = new DAO().findById(Course.class, 1);
		Subject scheme = cs591.getScheme();
		SchemeEditPanel panel = new SchemeEditPanel(scheme);
	}
	
	public void openStatisticsPanel() {
		
		Course cs591 = new DAO().findById(Course.class, 1);
		Section section = cs591.getSections().get(0);
		StatisticsPanel panel = new StatisticsPanel(section);
	}
}
