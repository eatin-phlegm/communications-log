package ui;

import com.opencsv.CSVWriter;
import ds.*;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Controller {

    /* top area */
    @FXML protected DatePicker datePicker;
    @FXML protected Button todayButton;
    @FXML protected Button reportWasteButton;
    @FXML protected Button requestTimeOffButton;
    @FXML protected Button compileWeekButton;

    @FXML protected Accordion accordionNode;

    /* log tab */
    @FXML protected TextArea logArea;
    /* sign tab */
    @FXML protected TextArea signArea; // sign tab

    /* cleaning list tab */
    @FXML protected TableView<Task> cleaningTable;
    @FXML protected TableColumn<Task, TextField> cleaningTaskCol;
    @FXML protected TableColumn<Task, CheckBox> cleaningTaskCompleteCol;
    @FXML protected Button addTaskButton;
    @FXML protected Button removeTaskButton;

    /* stock tab */
    @FXML protected TextArea lowArea;
    @FXML protected TextArea outArea;

    private Logbook ds;

    public Controller(){}


    void saveLogbook(){
        this.ds.saveToFile("logbook.sav");
    }
    private void loadLogbook(){
        Logbook loaded = Logbook.loadFromFile("logbook.sav");
        if (loaded == null) this.ds = new Logbook();
        else this.ds = new Logbook(loaded);
    }

    @FXML private void initialize() {
        /* -- INITIALIZE SAVE FILE --*/
        this.loadLogbook();

        /* -- INITIALIZE DATE PICKER FUNCTIONS-- */

        // adds functionality to datePicker
        datePicker.setOnAction(event -> {
            if(datePicker.getValue().isAfter(LocalDate.now()))  accordionNode.setDisable(true); // disables editing of log when date is set in the future
            else{
                accordionNode.setDisable(false); // re-enables log when date is not in future
                // update log to reflect current date if not in future
                Day d = ds.getLog(datePicker.getValue());
                if(d != null) { // if a day's record could be retrieved, set the gui to show the log texts
                    logArea.setText(d.getLog());
                    lowArea.setText(d.getLowStock());
                    outArea.setText(d.getOutStock());
                    signArea.setText(d.getSign());
                }else{ // otherwise set all the logs to nothing
                    logArea.setText("");
                    lowArea.setText("");
                    outArea.setText("");
                    signArea.setText("");
                }
                List<Task> tasks = ds.getCleaningList(datePicker.getValue());
                cleaningTable.getItems().clear();
                if(tasks != null)   cleaningTable.getItems().addAll(tasks); // if that week had a cleaning list, retrieve it
            }
        });
        datePicker.setValue(LocalDate.now());
        todayButton.setOnAction(event -> datePicker.setValue(LocalDate.now())); // sets the datePicker to the current date when today button pressed
        datePicker.fireEvent(new ActionEvent());


        /*-- INITIALIZE CLEANING LIST FUNCTIONS --*/

        // sets the cell factory for the cleaning task table
        cleaningTaskCompleteCol.setCellValueFactory(new TaskCompleteDefaultValueFactory());
        cleaningTaskCol.setCellValueFactory(new TaskDescDefaultValueFactory());
        // sets the functionality of adding and removing tasks buttons
        addTaskButton.setOnAction(e -> {
                cleaningTable.getItems().add(new Task("Untitled Task",false));
                ds.updateCleaningList(datePicker.getValue(), cleaningTable.getItems());

            });
        removeTaskButton.setOnAction(e -> {
                int index = cleaningTable.getSelectionModel().getSelectedIndex();
                if(index >= 0){
                    cleaningTable.getItems().remove(index);
                    ds.updateCleaningList(datePicker.getValue(), cleaningTable.getItems());
                }
            });


        /*-- INITIALIZE REPORTING BUTTONS --*/

        // initializing waste report button
        reportWasteButton.setOnAction(e -> {
            Dialog<WasteReport> d = new Dialog<>();
            // set dialog ui
            d.setTitle("Reporting Food Waste...");
            d.setWidth(150);
            d.setHeight(150);
            d.setResizable(false);
            VBox vBox = new VBox();
            // set ui for info to be gathered
            TextField productWasted = new TextField();
            TextField amountWasted = new TextField();
            vBox.getChildren().addAll(new Label("Product Name:"), productWasted, new Label("Amount Wasted:"), amountWasted);
            d.getDialogPane().setContent(vBox);
            d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.FINISH);
            d.setResultConverter(buttonType -> {
                if(buttonType == ButtonType.FINISH){
                    return new WasteReport(productWasted.getText(), amountWasted.getText(), LocalDate.now());
                }else return null;
            });
            Optional<WasteReport> wr = d.showAndWait();
            if(wr.isPresent()){
                WasteReport wasteReport = wr.get();
                ds.addWasteReport(LocalDate.now(), wasteReport);
            }
        });
        // initializing time off request button
        requestTimeOffButton.setOnAction(e -> {
            Dialog<TimeRequest> d = new Dialog<>();
            // set dialog ui
            d.setTitle("Requesting for Time Off...");
            d.setWidth(150);
            d.setHeight(250);
            d.setResizable(false);
            VBox vBox = new VBox();
            // set ui for info to be gathered
            TextField employeeName = new TextField();
            TextField reasonDesired = new TextField();
            HBox hbox = new HBox();
            DatePicker start = new DatePicker();
            DatePicker end = new DatePicker();
            hbox.getChildren().addAll(new Label("Start:"), start, new Label("End:"), end);
            vBox.getChildren().addAll(
                    new Label("Your Name:"), employeeName,
                    new Label("Reason Desired:"), reasonDesired, new Separator(),
                    new Label("Date(s) Desired Off:"), hbox,
                    new Label("If you are only requesting one day, set both to the same date.")
            );
            vBox.setSpacing(5);
            d.getDialogPane().setContent(vBox);
            d.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.FINISH);
            d.setResultConverter(buttonType -> {
                if(buttonType == ButtonType.FINISH){
                    return new TimeRequest(employeeName.getText(), reasonDesired.getText(), LocalDate.now(), start.getValue(), end.getValue());
                }else return null;
            });
            Optional<TimeRequest> tr = d.showAndWait();
            if(tr.isPresent()){
                TimeRequest timeRequest = tr.get();
                ds.addTimeOffRequest(LocalDate.now(), timeRequest);
            }
        });
        compileWeekButton.setOnAction(e -> {
            List<String[]> csvText = ds.getWeekCSV(datePicker.getValue());
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Compiling Weekly Report...");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files",".csv"));
            File chosenPath = fileChooser.showSaveDialog(datePicker.getScene().getWindow());
            if(!(chosenPath == null)) {
                try (FileWriter fw = new FileWriter(chosenPath);
                     CSVWriter csvWriter = new CSVWriter(fw)) {
                    csvWriter.writeAll(csvText);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // add listeners to logs
        ChangeListener<String> logListener = (observable, oldValue, newValue) -> {
            Day d = new Day(logArea.getText(), signArea.getText(), lowArea.getText(), outArea.getText());
            ds.updateLog(datePicker.getValue(), d);
        };
        logArea.textProperty().addListener(logListener);
        signArea.textProperty().addListener(logListener);
        lowArea.textProperty().addListener(logListener);
        outArea.textProperty().addListener(logListener);
    }

}
