package Panels;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

import Database.DAO;
import Model.Score;
import Model.Subject;
import Model.Course;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javax.persistence.*;

public class SchemeEditPanelController implements Initializable{
	
	private Subject scheme;
	private List<Subject> editList;
	private List<Subject> addList;
	private List<Subject> deleteList;
	
	// Constructor
	SchemeEditPanelController(Subject scheme){
		this.scheme = scheme;
	}
	
	// Connect to Scene Builder
	@FXML private Button removeButton;
	@FXML private Button editButton;
	@FXML private Label textLabel;
	@FXML private TableView<RowData> table;
	@FXML private TableColumn<RowData, String> components;
	@FXML private TableColumn<RowData, String> weight;
	@FXML private TableColumn<RowData, String> maxScore;
	@FXML private TableColumn<RowData, String> maxBonus;
	
	// Initialize panel
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textLabel.setText("Editing grading scheme for " + scheme.getLabel() +
				".\nPlease ensure all component weights add up to 100.");
		
		updateTable();
		components.setCellFactory(TextFieldTableCell.forTableColumn());
		weight.setCellFactory(TextFieldTableCell.forTableColumn());
		maxScore.setCellFactory(TextFieldTableCell.forTableColumn());
		maxBonus.setCellFactory(TextFieldTableCell.forTableColumn());
		
		editList = scheme.getChildren();
		addList = new ArrayList<Subject>();
		deleteList = new ArrayList<Subject>();
	}
	
	// Add button method
	public void addButtonPressed() {
		Subject child = new Subject("New Component");
		child.setParent(scheme);
		scheme.getChildren().add(child);
		addList.add(child);
		updateTable();
	}
	
	// Remove button method
	public void removeButtonPressed() {
		RowData selected = table.getSelectionModel().getSelectedItem();
	    
	    int selectedRow = table.getSelectionModel().getSelectedIndex();
	    
	    Subject removedChild = scheme.getChildren().get(selectedRow);
	    
	    try {
	    	editList.remove(removedChild);
	    }
	    catch(Exception e) {
	    	addList.remove(removedChild);
	    }
	    
	    deleteList.add(removedChild);
	    
	    table.getItems().remove(selected);
	    
	    selected = table.getSelectionModel().getSelectedItem();
	    if(selected == null) {
	    	hideButtons();
	    }
	}
	
	// Edit button method
	public void editButtonPressed() {
		int selectedRow = table.getSelectionModel().getSelectedIndex();
		
		Subject child = scheme.getChildren().get(selectedRow);
		
		Subject checkChild = new DAO().findById(Subject.class, child.getId());
		
		if(checkChild != null) {
			SchemeEditPanel editPanel = new SchemeEditPanel(child);
		}
		else {
			MessagePanel message = new MessagePanel("Please save before editing a new component.");
		}
		
	}
	
	// Confirm button method
	public void confirmButtonPressed() {
		double sum = 0;
		int size;

		List<Subject> children = scheme.getChildren();
		
		if(children != null) {
			size = children.size();
		} else {
			size = 0;
		}
		
		if(size != 0) {
			for(int i = 0; i < scheme.getChildren().size(); i++) {
				sum = sum + scheme.getChildren().get(i).getWeight();
			}
		}
		else {
			sum = 100;
		}
				
		if(sum != 100) {
			MessagePanel message = new MessagePanel("The sum of the component weights do not sum to 100%.");
		}
		else {
			System.out.println("Here?");
			for(Subject node : addList) {
				node.save();
			}
			for(Subject node : editList) {
				node.update();
			}
			for(Subject node : deleteList) {
				new DAO().delete(Subject.class, node.getId());
			}
						
			MessagePanel message = new MessagePanel("Updated scheme saved.");
		}
	}
	
	// Method to repopulate table
	public void updateTable() {
		List<Subject> children = scheme.getChildren();
		
		if(children == null) {
			table.setPlaceholder(new Label("No components currently set"));
		} else {
			//List<RowData> rowData = new ArrayList();
			ObservableList<RowData> list = FXCollections.observableArrayList();
			table.setItems(list);
			
			for(int i = 0; i < children.size(); i++) {
				list.add(new RowData(children.get(i).getLabel(),
						children.get(i).getWeight(),
						children.get(i).getMaxScore().getPoint(),
						children.get(i).getMaxScore().getBonus()));
			}
			components.setCellValueFactory(new PropertyValueFactory("components"));
			weight.setCellValueFactory(new PropertyValueFactory("weight"));
			maxScore.setCellValueFactory(new PropertyValueFactory("maxScore"));
			maxBonus.setCellValueFactory(new PropertyValueFactory("maxBonus"));
			
			table.setEditable(true);
			components.setEditable(true);
			weight.setEditable(true);
			maxScore.setEditable(true);
			maxBonus.setEditable(true);
		}
		
		hideButtons();
	}
	
	// Method for showing buttons on table selection
	public void listSelection() {
		RowData selected = table.getSelectionModel().getSelectedItem();
		
		if(selected != null) {
			showButtons();
		}
		else {
			hideButtons();
		}
	}
	
	// Method to hide buttons
	public void hideButtons() {
		removeButton.setDisable(true);
		editButton.setDisable(true);
	}
	
	// Method to show buttons
	public void showButtons() {
		removeButton.setDisable(false);
		editButton.setDisable(false);
	}
	
	// Method to save table after an edit
	public void saveTable(TableColumn.CellEditEvent<RowData, String> cellEditEvent) {
	    String newEntry = cellEditEvent.getNewValue();
	    String column = cellEditEvent.getTableColumn().getText();
	    int selectedRow = table.getSelectionModel().getSelectedIndex();
	    
	    // Edit component name
	    if(column.matches("Components")) {
	    	scheme.getChildren().get(selectedRow).setLabel(newEntry);
	    }
	    // Edit component weight
	    else if(column.matches("Weight")){
	    	try {
	    		scheme.getChildren().get(selectedRow).setWeight(Double.valueOf(newEntry));
	    	} 
	    	catch(Exception e){
	    		MessagePanel message = new MessagePanel("Invalid Entry.");
	    	}
	    }
	    // Edit component max score allowance
	    else if(column.matches("Max Score")){
	    	try {
	    		scheme.getChildren().get(selectedRow).getMaxScore().setPoint(Double.valueOf(newEntry));
	    	} 
	    	catch(Exception e){
	    		MessagePanel message = new MessagePanel("Invalid Entry.");
	    	}
	    }
	    // Edit component max bonus allowance
		else if(column.matches("Max Bonus")){
			try {
				scheme.getChildren().get(selectedRow).getMaxScore().setBonus(Double.valueOf(newEntry));
	    	} 
	    	catch(Exception e){
	    		MessagePanel message = new MessagePanel("Invalid Entry.");
	    	}
		}
	}
}

