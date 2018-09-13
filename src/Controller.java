import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Controller {

    private static class SavePacket implements Serializable{

        private boolean populated;

        // stock
        ArrayList<String> mondayOutStock;
        ArrayList<String> tuesdayOutStock;
        ArrayList<String> wednesdayOutStock;
        ArrayList<String> thursdayOutStock;
        ArrayList<String> fridayOutStock;
        ArrayList<String> saturdayOutStock;
        ArrayList<String> sundayOutStock;

        ArrayList<String> mondayLowStock;
        ArrayList<String> tuesdayLowStock;
        ArrayList<String> wednesdayLowStock;
        ArrayList<String> thursdayLowStock;
        ArrayList<String> fridayLowStock;
        ArrayList<String> saturdayLowStock;
        ArrayList<String> sundayLowStock;

        // common logs
        String mondayComm;
        String tuesdayComm;
        String wednesdayComm;
        String thursdayComm;
        String fridayComm;
        String saturdayComm;
        String sundayComm;

        // WRTT logs
        String mondayWRTT;
        String tuesdayWRTT;
        String wednesdayWRTT;
        String thursdayWRTT;
        String fridayWRTT;
        String saturdayWRTT;
        String sundayWRTT;

        // -- weekly logs ---------------------\\
        // cleaning list
        ArrayList<String> cleaningList;
        ArrayList<Boolean> cleaningCompletion;

        String importantChanges;
        String rosterChanges;

        String localDate;

        // fills controller with pre-populated SavePacket
        private void populateController(Controller controller){
            if(!populated) return; // check against empty save packet
            // low stock
            controller.mondayLowStockList.getItems().setAll(this.mondayLowStock);
            controller.tuesdayLowStockList.getItems().setAll(this.tuesdayLowStock);
            controller.wednesdayLowStockList.getItems().setAll(this.wednesdayLowStock);
            controller.thursdayLowStockList.getItems().setAll(this.thursdayLowStock);
            controller.fridayLowStockList.getItems().setAll(this.fridayLowStock);
            controller.saturdayLowStockList.getItems().setAll(this.saturdayLowStock);
            controller.sundayLowStockList.getItems().setAll(this.sundayLowStock);
            // out stock
            controller.mondayOutStockList.getItems().setAll(this.mondayOutStock);
            controller.tuesdayOutStockList.getItems().setAll(this.tuesdayOutStock);
            controller.wednesdayOutStockList.getItems().setAll(this.wednesdayOutStock);
            controller.thursdayOutStockList.getItems().setAll(this.thursdayOutStock);
            controller.fridayOutStockList.getItems().setAll(this.fridayOutStock);
            controller.saturdayOutStockList.getItems().setAll(this.saturdayOutStock);
            controller.sundayOutStockList.getItems().setAll(this.sundayOutStock);
            // common logs
            controller.commonLogSunday.setText(this.sundayComm);
            controller.commonLogMonday.setText(this.mondayComm);
            controller.commonLogTuesday.setText(this.tuesdayComm);
            controller.commonLogWednesday.setText(this.wednesdayComm);
            controller.commonLogThursday.setText(this.thursdayComm);
            controller.commonLogFriday.setText(this.fridayComm);
            controller.commonLogSaturday.setText(this.saturdayComm);
            // WRTT
            controller.wrttSunday.setText(this.sundayWRTT);
            controller.wrttMonday.setText(this.mondayWRTT);
            controller.wrttTuesday.setText(this.tuesdayWRTT);
            controller.wrttWednesday.setText(this.wednesdayWRTT);
            controller.wrttThursday.setText(this.thursdayWRTT);
            controller.wrttFriday.setText(this.fridayWRTT);
            controller.wrttSaturday.setText(this.saturdayWRTT);
            // Cleaning list
            controller.cleaningList.getItems().clear(); // clear out previous list
            for(int i = 0; i < this.cleaningList.size(); ++i){
                controller.cleaningList.getItems().add(new CheckBox(this.cleaningList.get(i)));
                controller.cleaningList.getItems().get(i).setSelected(this.cleaningCompletion.get(i));
            }
            // important changes
            controller.importantChangesLog.setText(this.importantChanges);
            // roster changes
            controller.rosterChangesLog.setText(this.rosterChanges);
            // date
            controller.setDate(LocalDate.parse(this.localDate));
        }
        private void populatePacket(Controller c){
            // out stock
            mondayOutStock = new ArrayList<>(c.mondayOutStockList.getItems());
            tuesdayOutStock = new ArrayList<>(c.tuesdayOutStockList.getItems());
            wednesdayOutStock = new ArrayList<>(c.wednesdayOutStockList.getItems());
            thursdayOutStock = new ArrayList<>(c.thursdayOutStockList.getItems());
            fridayOutStock = new ArrayList<>(c.fridayOutStockList.getItems());
            saturdayOutStock = new ArrayList<>(c.saturdayOutStockList.getItems());
            sundayOutStock = new ArrayList<>(c.sundayOutStockList.getItems());
            // low stock
            mondayLowStock = new ArrayList<>(c.mondayLowStockList.getItems());
            tuesdayLowStock = new ArrayList<>(c.tuesdayLowStockList.getItems());
            wednesdayLowStock = new ArrayList<>(c.wednesdayLowStockList.getItems());
            thursdayLowStock = new ArrayList<>(c.thursdayLowStockList.getItems());
            fridayLowStock = new ArrayList<>(c.fridayLowStockList.getItems());
            saturdayLowStock = new ArrayList<>(c.saturdayLowStockList.getItems());
            sundayLowStock = new ArrayList<>(c.sundayLowStockList.getItems());
            // comm logs
            mondayComm = c.commonLogMonday.getText();
            tuesdayComm = c.commonLogTuesday.getText();
            wednesdayComm = c.commonLogWednesday.getText();
            thursdayComm = c.commonLogThursday.getText();
            fridayComm = c.commonLogFriday.getText();
            saturdayComm = c.commonLogSaturday.getText();
            sundayComm = c.commonLogSunday.getText();
            // wrtt
            mondayWRTT = c.wrttMonday.getText();
            tuesdayWRTT = c.wrttTuesday.getText();
            wednesdayWRTT = c.wrttWednesday.getText();
            thursdayWRTT = c.wrttThursday.getText();
            fridayWRTT = c.wrttFriday.getText();
            saturdayWRTT = c.wrttSaturday.getText();
            sundayWRTT = c.wrttSunday.getText();
            // cleaning list
            cleaningList = new ArrayList<>();
            cleaningCompletion = new ArrayList<>();
            for(CheckBox cb : c.cleaningList.getItems()){
                cleaningList.add(cb.getText());
                cleaningCompletion.add(cb.isSelected());
            }
            // important changes
            importantChanges = c.importantChangesLog.getText();
            // roster changes
            rosterChanges = c.rosterChangesLog.getText();
            // date
            localDate = c.localDate.toString();

            populated = true;
        }
        private String getHTML(Controller c){
            // refill this with the current controller data
            populatePacket(c);
            // analyze stock
            SortedMap<String, Integer> stockIndex = new TreeMap<>();
            for(ArrayList<String> day : Arrays.asList(mondayOutStock, tuesdayOutStock, wednesdayOutStock, thursdayOutStock, fridayOutStock, saturdayOutStock, sundayOutStock) ){
                for(String e : day){
                    // if already reported out previously
                    if(stockIndex.containsKey(e)){
                        stockIndex.put(e, stockIndex.get(e) + 1);
                    }else{
                        stockIndex.put(e, 1);
                    }
                }
            }
            // process stock analysis into a string
            StringBuilder stockAnalysis = new StringBuilder("\n\n<h2>Stock Analysis</h2>\n"+
                    "<table>\n<tr>\n<th>Topping</th>\n<th>Times Exhausted This Week</th></tr>\n");
            stockIndex.forEach((String topping, Integer count) -> {
                stockAnalysis.append("<tr>\n<td>").append(topping).append("</td>\n<td>").append(count).append("</td>\n</tr>\n");
            });
            stockAnalysis.append("</table>\n\n");
            // process cleaning list into a string
            StringBuilder cleaning = new StringBuilder("\n\n<h2>Cleaning List</h2>\n<h3>Cleaning Completed</h3>\n<ul>\n");
            StringBuilder cleaningFailed = new StringBuilder("<h3>Cleaning Failed</h3>\n<ul>\n");
            for(int i = 0; i < cleaningList.size(); ++i){
                if(cleaningCompletion.get(i)) cleaning.append("<li>").append(cleaningList.get(i)).append("</li>\n");
                else cleaningFailed.append("<li>").append(cleaningList.get(i)).append("</li>\n");
            }
            cleaning.append("</ul>");
            cleaning.append(cleaningFailed);
            cleaning.append("</ul>\n\n");
            // process text into html
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta charset=\"UTF-8\">\n" +
                    "<title>Week of " + localDate + "</title>\n" +
                    "<style>"+
                    "#content, #page {\n" +
                    "width: 100%; \n" +
                    "margin: 0; \n" +
                    "float: none;\n" +
                    "}\n" +
                    "@page { margin: 2cm }\n" +
                    "\n" +
                    "body {\n" +
                    "font: 13pt Georgia, \"Times New Roman\", Times, serif;\n" +
                    "line-height: 1.3;\n" +
                    "background: #fff !important;\n" +
                    "color: #000;\n" +
                    "}\n" +
                    "\n" +
                    "h1 {\n" +
                    "font-size: 24pt;\n" +
                    "}\n" +
                    "\n" +
                    "h2, h3, h4 {\n" +
                    "font-size: 14pt;\n" +
                    "margin-top: 25px;\n" +
                    "}    \n" +
                    "h2 {\n" +
                    "font-size: 18pt;\n" +
                    "}\n" +
                    "h3 {\n" +
                    "font-size: 16 pt;\n" +
                    "}\n" +
                    "a {\n" +
                    "    page-break-inside:avoid\n" +
                    "}\n" +
                    "blockquote {\n" +
                    "    page-break-inside: avoid;\n" +
                    "}\n" +
                    "h1, h2, h3, h4, h5, h6 { page-break-after:avoid; \n" +
                    "     page-break-inside:avoid }\n" +
                    "img { page-break-inside:avoid; \n" +
                    "     page-break-after:avoid; }\n" +
                    "table, pre { page-break-inside:avoid }\n" +
                    "table, th, td {\n" +
                    "\ttext-align:center;\n" +
                    "\tborder: 1px solid black;\n"+
                    "}\n" +
                    "ul, ol, dl  { page-break-before:avoid }\n" +
                    "   \n" +
                    ".entry:after {\n" +
                    "color: #999 !important;\n" +
                    "font-size: 1em;\n" +
                    "padding-top: 30px;\n" +
                    "}\n" +
                    "#header:before {\n" +
                    "color: #777 !important;\n" +
                    "font-size: 1em;\n" +
                    "padding-top: 30px;\n" +
                    "text-align: center !important;    \n" +
                    "}\n" +
                    "\n" +
                    "p, address, li, dt, dd, blockquote {\n" +
                    "font-size: 100%\n" +
                    "}\n" +
                    "\n" +
                    "code, pre { font-family: \"Courier New\", Courier, mono}\n" +
                    "\n" +
                    "ul, ol {\n" +
                    "list-style: square; margin-left: 18pt;\n" +
                    "margin-bottom: 20pt;    \n" +
                    "}\n" +
                    "\n" +
                    "li {\n" +
                    "line-height: 1.6em;\n" +
                    "}   "+
                    "</style>"+
                    "</head>\n" + "\n" +
                    "<body>\n" +
                    // content of document
                    "<h1>Week of " + localDate + "</h1>\n" +
                    "<h2>Week Notices</h2>\n" +
                    // roster changes, if any
                    (((rosterChanges!=null)&&(!rosterChanges.isEmpty()))?("<h3>Roster Changes</h3>\n<p>"+rosterChanges+"</p>\n"):"<p>No roster changes to report...</p>\n")+
                    // important notes, if any
                    (((importantChanges!=null)&&(!importantChanges.isEmpty()))?("<h3>Important Notices</h3>\n<p>"+importantChanges+"</p>\n"):"<p>No significant changes to report...</p>\n")+
                    // cleaning list
                    cleaning+
                    // stock analysis
                    stockAnalysis+
                    // daily common logs and who read them
                    "<h2>Daily Common Logs</h2>\n"+

                    "<h3>Monday</h3>\n"+
                    "<p>"+(( (mondayComm!=null) && (!mondayComm.isEmpty()) )? mondayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (mondayWRTT!=null) && (!mondayWRTT.isEmpty()) )? mondayWRTT :"No one read it...")+"</p>\n"+

                    "<h3>Tuesday</h3>\n"+
                    "<p>"+(( (tuesdayComm!=null) && (!tuesdayComm.isEmpty()) )? tuesdayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (tuesdayWRTT!=null) && (!tuesdayWRTT.isEmpty()) )? tuesdayWRTT :"No one read it...")+"</p>\n"+

                    "<h3>Wednesday</h3>\n"+
                    "<p>"+(( (wednesdayComm!=null) && (!wednesdayComm.isEmpty()) )? wednesdayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (wednesdayWRTT!=null) && (!wednesdayWRTT.isEmpty()) )? wednesdayWRTT :"No one read it...")+"</p>\n"+

                    "<h3>Thursday</h3>\n"+
                    "<p>"+(( (thursdayComm!=null) && (!thursdayComm.isEmpty()) )? thursdayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (thursdayWRTT!=null) && (!thursdayWRTT.isEmpty()) )? thursdayWRTT :"No one read it...")+"</p>\n"+

                    "<h3>Friday</h3>\n"+
                    "<p>"+(( (fridayComm!=null) && (!fridayComm.isEmpty()) )? fridayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (fridayWRTT!=null) && (!fridayWRTT.isEmpty()) )? fridayWRTT :"No one read it...")+"</p>\n"+

                    "<h3>Saturday</h3>\n"+
                    "<p>"+(( (saturdayComm!=null) && (!saturdayComm.isEmpty()) )? saturdayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (saturdayWRTT!=null) && (!saturdayWRTT.isEmpty()) )? saturdayWRTT :"No one read it...")+"</p>\n"+

                    "<h3>Sunday</h3>\n"+
                    "<p>"+(( (sundayComm!=null) && (!sundayComm.isEmpty()) )? sundayComm :"Nothing to report...")+"</p>\n"+
                    "<h6>Read by:</h6>\n"+
                    "<p>"+(( (sundayWRTT!=null) && (!sundayWRTT.isEmpty()) )? sundayWRTT :"No one read it...")+"</p>\n"+

                    "</body>\n" + "\n" +
                    "</html>";

            // return html as a string
            return html;
        }
    }

    // ---------- Daily Logs -------------------------- \\
    // Monday
    @FXML private ListView<String> mondayOutStockList;
    @FXML private MenuButton    mondayOutStockButton;
    @FXML private MenuButton    mondayLowStockButton;
    @FXML private ListView<String> mondayLowStockList;
    @FXML private TextArea      commonLogMonday;
    @FXML private TextArea      wrttMonday;

    // Tuesday
    @FXML private ListView<String> tuesdayLowStockList;
    @FXML private MenuButton    tuesdayLowStockButton;
    @FXML private ListView<String> tuesdayOutStockList;
    @FXML private MenuButton    tuesdayOutStockButton;
    @FXML private TextArea      commonLogTuesday;
    @FXML private TextArea      wrttTuesday;

    // Wednesday
    @FXML private ListView<String> wednesdayLowStockList;
    @FXML private MenuButton    wednesdayLowStockButton;
    @FXML private ListView<String> wednesdayOutStockList;
    @FXML private MenuButton    wednesdayOutStockButton;
    @FXML private TextArea      commonLogWednesday;
    @FXML private TextArea      wrttWednesday;

    // Thursday
    @FXML private ListView<String> thursdayLowStockList;
    @FXML private MenuButton    thursdayLowStockButton;
    @FXML private ListView<String> thursdayOutStockList;
    @FXML private MenuButton    thursdayOutStockButton;
    @FXML private TextArea      commonLogThursday;
    @FXML private TextArea      wrttThursday;

    // Friday
    @FXML private MenuButton    fridayOutStockButton;
    @FXML private ListView<String> fridayOutStockList;
    @FXML private MenuButton    fridayLowStockButton;
    @FXML private ListView<String> fridayLowStockList;
    @FXML private TextArea      commonLogFriday;
    @FXML private TextArea      wrttFriday;

    // Saturday
    @FXML private TextArea      commonLogSaturday;
    @FXML private TextArea      wrttSaturday;
    @FXML private MenuButton    saturdayOutStockButton;
    @FXML private ListView<String> saturdayOutStockList;
    @FXML private MenuButton    saturdayLowStockButton;
    @FXML private ListView<String> saturdayLowStockList;

    // Sunday
    @FXML private ListView<String> sundayLowStockList;
    @FXML private MenuButton    sundayLowStockButton;
    @FXML private ListView<String> sundayOutStockList;
    @FXML private MenuButton    sundayOutStockButton;
    @FXML private TextArea      commonLogSunday;
    @FXML private TextArea      wrttSunday;

    // --------------- Cleaning List -------------------------- \\
    @FXML private ListView<CheckBox>    cleaningList;
    @FXML private TextField             newCleaningItemText;

    // ---------------- Weekly Logs ---------------------------- \\
    @FXML private TextArea      importantChangesLog;
    @FXML private TextArea      rosterChangesLog;

    // --------- Tabs ------------ \\
    @FXML private Tab mondayTab;
    @FXML private Tab tuesdayTab;
    @FXML private Tab wednesdayTab;
    @FXML private Tab thursdayTab;
    @FXML private Tab fridayTab;
    @FXML private Tab saturdayTab;
    @FXML private Tab sundayTab;

    // ------- Date picker ---------------- \\
    @FXML private DatePicker    datePicker;
    private LocalDate           localDate;


    public Controller(){}

    // used to provide interface functionality for stock list MenuButtons
    private void populateMenuButton(MenuButton button, List<String> defaultStockList, ListView<String> buttonStockList){
        for(String e : defaultStockList){
            MenuItem item = new MenuItem(e);
            item.setOnAction(event -> {
                if(!buttonStockList.getItems().contains(item.getText())) buttonStockList.getItems().add(item.getText());
            });
            button.getItems().add(item);
        }
    }

    @FXML private void initialize(){
        // Cleaning List
        ObservableList<CheckBox> tasks = FXCollections.observableArrayList();
        cleaningList.setItems(tasks);
        cleaningList.setDisable(false);

        // import default stock list from file and load as MenuItems into all the MenuButtons
        List<String> defaultStockList;
        try {
            // retrieve list from file
            defaultStockList = Files.readAllLines( Paths.get("defaultStockList.txt"), StandardCharsets.UTF_8);
            // sort list alphabetically
            Collections.sort(defaultStockList);
            // add list to menu buttons
            populateMenuButton(mondayLowStockButton, defaultStockList, mondayLowStockList);
            populateMenuButton(mondayOutStockButton, defaultStockList, mondayOutStockList);

            populateMenuButton(tuesdayLowStockButton, defaultStockList, tuesdayLowStockList);
            populateMenuButton(tuesdayOutStockButton, defaultStockList, tuesdayOutStockList);

            populateMenuButton(wednesdayLowStockButton, defaultStockList, wednesdayLowStockList);
            populateMenuButton(wednesdayOutStockButton, defaultStockList, wednesdayOutStockList);

            populateMenuButton(thursdayLowStockButton, defaultStockList, thursdayLowStockList);
            populateMenuButton(thursdayOutStockButton, defaultStockList, thursdayOutStockList);

            populateMenuButton(fridayLowStockButton, defaultStockList, fridayLowStockList);
            populateMenuButton(fridayOutStockButton, defaultStockList, fridayOutStockList);

            populateMenuButton(saturdayLowStockButton, defaultStockList, saturdayLowStockList);
            populateMenuButton(saturdayOutStockButton, defaultStockList, saturdayOutStockList);

            populateMenuButton(sundayLowStockButton, defaultStockList, sundayLowStockList);
            populateMenuButton(sundayOutStockButton, defaultStockList, sundayOutStockList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // used for adding cleaning tasks to the weekly cleaning checklist
    @FXML protected void addCleaningItem(){
        CheckBox isDone = new CheckBox();
        isDone.setText(newCleaningItemText.getText());
        isDone.setSelected(false);
        isDone.setDisable(false);
        cleaningList.getItems().add(isDone);
    }

    // used to compile the weekly report
    @FXML protected void compileReport(){

        // handle for when there is no date selected
        if(localDate == null){
            Alert needDateAlert = new Alert(Alert.AlertType.ERROR);
            needDateAlert.setTitle("Need a start date to compile!");
            needDateAlert.setContentText("Please select the date of this week's Monday in the date picker at the bottom of the window.");
            needDateAlert.showAndWait();
            return;
        }

        // show file select dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose report file location...");
        // make html only available file type
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Document", "*.html"));
        File report =  fileChooser.showSaveDialog(null);

        // handle for when no file was selected
        if(report == null){
            Alert needDateAlert = new Alert(Alert.AlertType.ERROR);
            needDateAlert.setTitle("No file selected!");
            needDateAlert.setContentText("Please select a file to compile this week's report into.");
            needDateAlert.showAndWait();
        }else {
            // write to html file
            try {
                FileWriter fileWriter = new FileWriter(report);
                PrintWriter printer = new PrintWriter(fileWriter);
                SavePacket currentState = new SavePacket();
                printer.print(currentState.getHTML(this));
                printer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // when File + Save is selected
    @FXML protected void save(){
        // handle for when there is no date selected
        if(localDate == null){
            Alert needDateAlert = new Alert(Alert.AlertType.ERROR);
            needDateAlert.setTitle("Need a start date to compile!");
            needDateAlert.setContentText("Please select the date of this week's Monday in the date picker at the bottom of the window.");
            needDateAlert.showAndWait();
            return;
        }
        // show file select dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose save file location...");
        // make html only available file type
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Communications Log file", "*.commlog"));
        File saveFile =  fileChooser.showSaveDialog(null);

        // handle for when no file was selected
        if(saveFile == null){
            Alert needDateAlert = new Alert(Alert.AlertType.ERROR);
            needDateAlert.setTitle("No file selected!");
            needDateAlert.setContentText("Please select a file to save to.");
            needDateAlert.showAndWait();
        }else{
            try {
                FileOutputStream fos = new FileOutputStream(saveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                SavePacket save = new SavePacket();
                save.populatePacket(this);
                oos.writeObject(save);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // when File + Open is selected
    @FXML protected void open(){
        // show file select dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file...");
        // make html only available file type
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Communications Log file", "*.commlog"));
        File saveFile =  fileChooser.showOpenDialog(null);

        // handle for when no file was selected
        if(saveFile == null){
            Alert needDateAlert = new Alert(Alert.AlertType.ERROR);
            needDateAlert.setTitle("No file selected!");
            needDateAlert.setContentText("Please select a file to open.");
            needDateAlert.showAndWait();
        }else{
            try {
                FileInputStream fis = new FileInputStream(saveFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                SavePacket save = (SavePacket) ois.readObject();
                ois.close();
                save.populateController(this);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    // when File + New is selected
    // clears all data
    @FXML protected void nextWeek(){
        // low stock
        mondayLowStockList.getItems().clear();
        tuesdayLowStockList.getItems().clear();
        wednesdayLowStockList.getItems().clear();
        thursdayLowStockList.getItems().clear();
        fridayLowStockList.getItems().clear();
        saturdayLowStockList.getItems().clear();
        sundayLowStockList.getItems().clear();
        // out stock
        mondayOutStockList.getItems().clear();
        tuesdayOutStockList.getItems().clear();
        wednesdayOutStockList.getItems().clear();
        thursdayOutStockList.getItems().clear();
        fridayOutStockList.getItems().clear();
        saturdayOutStockList.getItems().clear();
        sundayOutStockList.getItems().clear();
        // common logs
        commonLogSunday.clear();
        commonLogMonday.clear();
        commonLogTuesday.clear();
        commonLogWednesday.clear();
        commonLogThursday.clear();
        commonLogFriday.clear();
        commonLogSaturday.clear();
        // WRTT
        wrttSunday.clear();
        wrttMonday.clear();
        wrttTuesday.clear();
        wrttWednesday.clear();
        wrttThursday.clear();
        wrttFriday.clear();
        wrttSaturday.clear();
        // Cleaning list
        cleaningList.getItems().clear(); // clear out previous list
        // important changes
        importantChangesLog.clear();
        // roster changes
        rosterChangesLog.clear();
        // date
        if(datePicker.getValue() != null) setDate(localDate.plusDays(7));
    }

    // used to set the date of the week
    @FXML protected void setDate(){
        // handle for when date is not monday
        if(datePicker.getValue().getDayOfWeek() != DayOfWeek.MONDAY){
            // reset date tabs
            localDate = null;
            mondayTab.setText("Monday");
            tuesdayTab.setText("Tuesday");
            wednesdayTab.setText("Wednesday");
            thursdayTab.setText("Thursday");
            fridayTab.setText("Friday");
            saturdayTab.setText("Saturday");
            sundayTab.setText("Sunday");
            datePicker.getEditor().clear();

            Alert needDateAlert = new Alert(Alert.AlertType.ERROR);
            needDateAlert.setTitle("Select the MONDAY of the current week!");
            needDateAlert.setContentText("The selected date must be a Monday!");
            needDateAlert.showAndWait();
            return;
        }
        // set date
        localDate = datePicker.getValue();

        // set tab dates
        mondayTab.setText("Monday   "+localDate.getMonthValue()+"/"+localDate.getDayOfMonth());
        tuesdayTab.setText("Tuesday   "+localDate.plusDays(1).getMonthValue()+"/"+localDate.plusDays(1).getDayOfMonth());
        wednesdayTab.setText("Wednesday   "+localDate.plusDays(2).getMonthValue()+"/"+localDate.plusDays(2).getDayOfMonth());
        thursdayTab.setText("Thursday   "+localDate.plusDays(3).getMonthValue()+"/"+localDate.plusDays(3).getDayOfMonth());
        fridayTab.setText("Friday   "+localDate.plusDays(4).getMonthValue()+"/"+localDate.plusDays(4).getDayOfMonth());
        saturdayTab.setText("Saturday   "+localDate.plusDays(5).getMonthValue()+"/"+localDate.plusDays(5).getDayOfMonth());
        sundayTab.setText("Sunday   "+localDate.plusDays(6).getMonthValue()+"/"+localDate.plusDays(6).getDayOfMonth());
    }
    
    private void setDate(LocalDate date){
        // set date
        localDate = date;
        datePicker.setValue(localDate);

        // set tab dates
        mondayTab.setText("Monday   "+localDate.getMonthValue()+"/"+localDate.getDayOfMonth());
        tuesdayTab.setText("Tuesday   "+localDate.plusDays(1).getMonthValue()+"/"+localDate.plusDays(1).getDayOfMonth());
        wednesdayTab.setText("Wednesday   "+localDate.plusDays(2).getMonthValue()+"/"+localDate.plusDays(2).getDayOfMonth());
        thursdayTab.setText("Thursday   "+localDate.plusDays(3).getMonthValue()+"/"+localDate.plusDays(3).getDayOfMonth());
        fridayTab.setText("Friday   "+localDate.plusDays(4).getMonthValue()+"/"+localDate.plusDays(4).getDayOfMonth());
        saturdayTab.setText("Saturday   "+localDate.plusDays(5).getMonthValue()+"/"+localDate.plusDays(5).getDayOfMonth());
        sundayTab.setText("Sunday   "+localDate.plusDays(6).getMonthValue()+"/"+localDate.plusDays(6).getDayOfMonth());
    }
}
