package View.components;

import Model.Class;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.Main;

public class ClassSelect extends ComboBox<Class> {

    public ClassSelect() {
        ObservableList<Class> myComboBoxData = FXCollections.observableArrayList();
        myComboBoxData.addAll(Main.classes);
        setItems(myComboBoxData);
    }
}
