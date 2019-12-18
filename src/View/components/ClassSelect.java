package View.components;

import Database.DAO;
import Model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import View.Main;

import java.util.List;

public class ClassSelect extends ComboBox<Course> {
    public ClassSelect(boolean showEmpty) {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        List<Course> coursesQuery = (List<Course>) new DAO().query("from Course");
        if (showEmpty) {
            coursesQuery.add(Main.EMPTY);
        }
        courseList.addAll(coursesQuery);
        setItems(courseList);
        if (!courseList.isEmpty()) {
            setValue(courseList.get(0));
        }
    }

}
