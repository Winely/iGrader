package Controller;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Main;

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
		List<Student> students = section.getStudents().stream()
				.filter(student -> !student.isFrozen())
				.collect(Collectors.toList());
		List<Student> filteredStudents;
		Subject scheme = section.getCourse().getScheme();
		
		boolean type;
		ObservableList<Statistic> list = FXCollections.observableArrayList();
		
		// Get tab selection
		int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
		
		if(selectedTab == 0) {
			filteredStudents = students;
		}
		else{
			type = selectedTab == 1;
			filteredStudents = students.stream()
					.filter(student -> student.isGrad() == type)
					.collect(Collectors.toList());
		}

		if (filteredStudents.isEmpty()) return;

		// Loop through the different categories
		for(Subject subject : scheme.getChildren()) {
			list.add(getStatisticBySubject(subject.getLabel(), subject, filteredStudents));
		}
		list.add(getStatisticBySubject("Total", scheme, filteredStudents));

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

	private Statistic getStatisticBySubject(String label, Subject subject, List<Student> students){
		List<Double> subjectGrades = new ArrayList<>();
		double subjectSum = 0.0;
		double count = 0.0;

		for(Student student : students) {
			Score score = subject.getScoreByStudent(student);
			subjectGrades.add((score.getPoint() + score.getBonus()/(subject.getMaxScore().getPoint())));
			subjectSum = subjectSum + (score.getPoint() + score.getBonus()/(subject.getMaxScore().getPoint()));
			count += 1;
		}

		// Calculate statistics
		double min = Collections.min(subjectGrades);
		double mean = subjectSum/count;
		double max = Collections.max(subjectGrades);
		double med;

		// Median
		Collections.sort(subjectGrades);

		if(subjectGrades.size() %2 == 0) {
			med = subjectGrades.get(subjectGrades.size()/2);
		} else {
			med = (subjectGrades.get((subjectGrades.size() - 1)/2) + subjectGrades.get((subjectGrades.size())/2))/2;
		}

		// Std Dev
		double stdSum = 0;
		for (int k = 0; k < count; k++) {
			stdSum =stdSum + Math.pow((subjectGrades.get(k) - (subjectSum/count)), 2);
		}
		double std = Math.sqrt(stdSum / ( count - 1 ));
		std = Math.round(std * 100.0) / 100.0;
		return new Statistic(label, min, med, mean, max, std);
	}
	
	public void curve() {
		section.setCurve(Double.parseDouble(curveText.getText()));
		section.update();
		getStatistics();
		Main.handle(Main.UPDATE);
	}
	
}

