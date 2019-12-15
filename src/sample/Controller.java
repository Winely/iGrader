package sample;

import java.util.ArrayList;
import java.util.List;

import Database.DAO;
import Model.Course;
import Model.Name;
import Model.Score;
import Model.Section;
import Model.Student;
import Model.Subject;
import Panels.SchemeEditPanel;
import Panels.StatisticsPanel;

public class Controller {
	
	public void openSchemeEditPanel() {
		DAO dao = new DAO();
		
		Course cs591 = new DAO().findById(Course.class, 3);
		Subject scheme = cs591.getScheme();
		SchemeEditPanel panel = new SchemeEditPanel(scheme);
	}
	
	public void openStatisticsPanel() {
		
		Course cs591 = new DAO().findById(Course.class, 3);
		Section section = cs591.getSections().get(0);
		StatisticsPanel panel = new StatisticsPanel(section);
	}
}
