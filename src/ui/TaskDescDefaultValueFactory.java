package ui;
import ds.Task;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TaskDescDefaultValueFactory implements Callback<TableColumn.CellDataFeatures<Task, TextField>, ObservableValue<TextField>> {
@Override
    public ObservableValue<TextField> call(TableColumn.CellDataFeatures<Task, TextField> param) {
        Task task = param.getValue();
        TextField textField = new TextField();
        textField.textProperty().setValue(task.getDescription());
        textField.textProperty().addListener((ov, old_val, new_val) -> task.setDescription(new_val));
        return new SimpleObjectProperty<>(textField);
    }
}
