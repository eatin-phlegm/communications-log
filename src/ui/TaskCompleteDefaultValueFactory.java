package ui;
import ds.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TaskCompleteDefaultValueFactory implements Callback<TableColumn.CellDataFeatures<Task, CheckBox>, ObservableValue<CheckBox>> {
    @Override
    public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Task, CheckBox> param) {
        Task task = param.getValue();
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().setValue(task.isComplete());
        checkBox.selectedProperty().addListener((ov, old_val, new_val) -> task.setComplete(new_val));
        return new SimpleObjectProperty<>(checkBox);
    }
}