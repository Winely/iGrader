package Panels;

import java.net.URL;
import java.util.*;

import Model.Course;
import Model.Score;
import Model.Section;
import Model.Student;
import Model.Subject;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Main;

import javax.persistence.*;

public class StatisticsPanelController implements Initializable{

	Section section;
	@FXML private TableView<Statistic> table;
	@FXML private TableColumn<Statistic, String> subjectColumn;
	@FXML private TableColumn<Statistic, Double> minColumn;
	@FXML private TableColumn<Statistic, Double> medianColumn;
	@FXML private TableColumn<Statistic, Double> meanColumn;
	@FXML private TableColumn<Statistic, Double> devColumn;
	@FXML private TableColumn<Statistic, Double> maxColumn;
	@FXML private TextField curveText;
	@FXML private Button applyButton;
	@FXML private TabPane tabPane;
	
	public StatisticsPanelController(Section section) {
		this.section = section;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tabPane.getSelectionModel().select(0);
		curveText.setText(Double.toString(section.getCurve()));
		getStatistics();
	}
	
	public void getStatistics() {
		List<Student> students = section.getStudents();
		List<Student> filteredStudents = new ArrayList<Student>();
		List<Double> subjectGrades;
		Double subjectSum;
		Double count;
		List<Double> totalGrades = new ArrayList<>();
		TableColumn<Statistic, Double> newColumn;
		
		Subject scheme = section.getCourse().getScheme();
		List<Subject> categories = scheme.getChildren();
		
		ObservableList<Double> data;
		List<Statistic> classStats;
		Statistic stat;
		Subject category;
		Score score;
		
		Double min;
		Double max;
		Double mean;
		Double std;
		Double med;
		
		Boolean type;
		ObservableList<Statistic> list = FXCollections.observableArrayList();
		
		// Get tab selection
		int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
		
		if(selectedTab == 0) {
			filteredStudents = students;
		}
		else{
			if(selectedTab == 1) {
				type = false;
			}
			else {
				type = true;
			}
			
			// Filter students based on tab selection
			for(int i = 0; i < students.size(); i++) {
			
				// Graduates
				if(type == students.get(i).isGrad()) {
					filteredStudents.add(students.get(i));
				}
			}
		}
		
		// Loop through the different categories
		for(int j = 0; j < categories.size(); j++) {
			subjectGrades = new ArrayList<>();
			category = categories.get(j);
			
			subjectSum = 0.0;
			count = 0.0;
			
			for(int i = 0; i < filteredStudents.size(); i++) {
				
				score = category.getScoreByStudent(filteredStudents.get(i));
				subjectGrades.add((score.getPoint() + score.getBonus()/(category.getMaxScore().getPoint())));
				
				subjectSum = subjectSum + (score.getPoint() + score.getBonus()/(category.getMaxScore().getPoint()));
				
				count = count + 1.0;
			}	
			
			// Calculate statistics
			min = Collections.min(subjectGrades);
			mean = subjectSum/count;
			max = Collections.max(subjectGrades);
			
			// Median
			Collections.sort(subjectGrades);
			
			if(subjectGrades.size() %2 == 0) {
				med = subjectGrades.get(subjectGrades.size()/2);
			}
			else {
				med = (subjectGrades.get((subjectGrades.size() - 1)/2) + subjectGrades.get((subjectGrades.size())/2))/2;
			}
			
			// Std Dev
			double stdSum = 0;
			for (int k = 0; k < count; k++) {
				stdSum =stdSum + Math.pow((subjectGrades.get(k) - (subjectSum/count)), 2);
			}
	        std = Math.sqrt(stdSum / ( count - 1 ));
	        std = Math.round(std * 100.0) / 100.0;
	        
	        // Create list of student grades
			list.add(new Statistic(categories.get(j).getLabel(), min, med, mean, max, std));
		}
		
		// Statistics for entire class... VERY BAD CODE!!! DO NOT LOOK AT THIS PLEASE!!!
		subjectGrades = new ArrayList<>();
		subjectSum = 0.0;
		
		for(int i = 0; i < filteredStudents.size(); i++) {
			subjectGrades.add(scheme.getFinalScoreByStudent(filteredStudents.get(i)).getPoint());
			subjectSum = subjectSum + scheme.getFinalScoreByStudent(filteredStudents.get(i)).getPoint();
		}
		
		// Calculate statistics
		min = Collections.min(subjectGrades);
		mean = subjectSum/filteredStudents.size();
		max = Collections.max(subjectGrades);
								
		// Median
		Collections.sort(subjectGrades);
							
		if(subjectGrades.size() %2 == 0) {
			med = subjectGrades.get(subjectGrades.size()/2);
		}
		else {
			med = (subjectGrades.get((subjectGrades.size() - 1)/2) + subjectGrades.get((subjectGrades.size())/2))/2;
		}
								
		// Std Dev
		double stdSum = 0;
		for (int k = 0; k < filteredStudents.size(); k++) {
			stdSum =stdSum + Math.pow((subjectGrades.get(k) - (subjectSum/filteredStudents.size())), 2);
		}
		std = Math.sqrt(stdSum / ( filteredStudents.size() - 1 ));
		std = Math.round(std * 100.0) / 100.0;
					
		list.add(new Statistic("Total", min, med, mean, max, std));
		
		// Set table factories
		table.setItems(list);
		table.refresh();
		
		subjectColumn.setCellValueFactory(new PropertyValueFactory("subject"));
		minColumn.setCellValueFactory(new PropertyValueFactory("minimum"));
		medianColumn.setCellValueFactory(new PropertyValueFactory("median"));
		meanColumn.setCellValueFactory(new PropertyValueFactory("mean"));
		devColumn.setCellValueFactory(new PropertyValueFactory("deviation"));
		maxColumn.setCellValueFactory(new PropertyValueFactory("maximum"));
		
	}
	
	public void curve() {
		section.setCurve(Double.valueOf(curveText.getText()));
		section.update();
		getStatistics();
		Main.handle(Main.UPDATE);
	}
	
}

