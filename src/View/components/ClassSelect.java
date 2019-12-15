package View.components;

import Database.DAO;
import Model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import sample.Main;

import java.util.ArrayList;

public class ClassSelect extends ComboBox<Course> {

    private DAO dao = new DAO();

    public ClassSelect(boolean showEmpty) {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        ArrayList<Course> coursesQuery = (ArrayList<Course>) DAO.query("from Course");
        if (showEmpty) {
            coursesQuery.add(Main.EMPTY);
        }
        courseList.addAll(coursesQuery);
        setItems(courseList);
    }

}
