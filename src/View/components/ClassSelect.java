package View.components;

import Database.DAO;
import Model.Course;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import sample.Main;

import java.util.ArrayList;

public class ClassSelect extends ComboBox<Course> {

    private DAO dao = new DAO();

    public ClassSelect() {
        ObservableList<Course> courseList = FXCollections.observableArrayList();
        ArrayList<Course> courses = (ArrayList<Course>) DAO.query("from Course");
        if (courses.size() == 0) {
            setItems(courseList);
        } else {
            courseList.addAll(courses);
            setCellFactory(courseListView -> new CourseListCell());
            setButtonCell(new CourseListCell());
            setItems(courseList);
        }
    }

    public void addListItem(Course course) {
        getItems().add(course);
    }

    class CourseListCell extends ListCell<Course> {

        @Override
        protected void updateItem(Course course, boolean empty) {
            super.updateItem(course, empty) ;
            if (empty) {
                setText(null);
            } else {
                setText(course.getScheme().getLabel());
            }
        }
    }
}
