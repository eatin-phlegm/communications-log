package ds;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class WeeklyReport implements Serializable {

    private LocalDate startingMonday;
    private List<Task> cleaningList;
    private Stack<TimeRequest> timeOffRequests;
    private List<WasteReport> wasteLogReport;
    private Day[] weekLog;

    WeeklyReport(LocalDate startingMonday){
        this.startingMonday = startingMonday;
        this.timeOffRequests = new Stack<>();
        this.wasteLogReport = new ArrayList<>();
        weekLog = new Day[7];
    }

    private int dayToIndex(DayOfWeek d){
        switch (d){

            case MONDAY:
                return 0;
            case TUESDAY:
                return 1;
            case WEDNESDAY:
                return 2;
            case THURSDAY:
                return 3;
            case FRIDAY:
                return 4;
            case SATURDAY:
                return 5;
            case SUNDAY:
                return 6;
            default:
                return -1;
        }
    }

    void updateLog(LocalDate day, Day newContent){
        weekLog[dayToIndex(day.getDayOfWeek())] = new Day(newContent);
    }

    void updateCleaningList(List<Task> tasks){
        if(tasks != null) {
            this.cleaningList = new ArrayList<>(tasks);
        }
    }
    void addWasteReport(WasteReport wr){
        this.wasteLogReport.add(wr);
    }
    void addTimeOffRequest(TimeRequest tr){
        this.timeOffRequests.push(tr);
    }
    Day getDayLogs(LocalDate toView){
        return weekLog[dayToIndex(toView.getDayOfWeek())];
    }
    List<Task> getCleaningList(){
        return this.cleaningList;
    }


    /* --  Compile CSV file functions  -- */

    private List<String[]> compileTimeOff(){
        List<String[]> pto  = new ArrayList<>();
        // add header of personal time off
        pto.add(new String[]{"TIME OFF REQUESTS:"});
        // add data of personal time off
        if(timeOffRequests == null || timeOffRequests.isEmpty()) pto.add(new String[]{"No time off requests this week..."});
        else {
            pto.add(new String[]{"Employee","Date Submitted", "Time Off Desired", "Reason Desired"});
            pto.add(new String[]{"--------","--------------", "----------------", "--------------"});

            for (TimeRequest tr : timeOffRequests) {
                String[] current = {
                        tr.getEmployee(),
                        tr.getTimeRequested().toString(),
                        tr.getDesiredStart().toString() + "--" + tr.getDesiredEnd().toString(),
                        tr.getReason()
                };
                pto.add(current);
            }
            pto.add(new String[]{""});
            pto.add(new String[]{"Please record the PTO requests you approve in the calendar."});
        }

        return pto;
    }

    private List<String[]> compileWaste(){
        List<String[]> waste = new ArrayList<>();
        waste.add(new String[]{"WASTE LOG:"});
        // add waste data
        if(wasteLogReport == null || wasteLogReport.isEmpty()) waste.add(new String[]{"No waste to report for this week. Great job!"});
        else{
            waste.add(new String[]{"Item","Amount","Date"});
            waste.add(new String[]{"----","------","----"});

            for(WasteReport wr : wasteLogReport){
                String[] current = {
                        wr.getItem(),
                        wr.getWasteAmount(),
                        wr.getTimeSubmitted().toString()
                };
                waste.add(current);
            }
        }
        return waste;
    }

    private List<String[]> compileCleaningList(){
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"WEEKLY CLEANING LIST:"});

        if(cleaningList == null || cleaningList.isEmpty()) list.add(new String[]{"No cleaning tasks were scheduled for this week..."});
        else {
            list.add(new String[]{"Task Description","Completed?"});
            list.add(new String[]{"----------------","----------"});

            for(Task t : cleaningList) list.add(new String[]{ t.getDescription(), ((t.isComplete())? "Yes" : "No" ) });
        }

        return list;
    }

    // for stock and general logs
    private List<String[]> compileDailyLogs(){
        List<String[]> dailyLogs = new ArrayList<>();
        dailyLogs.add(new String[]{"DAILY LOGS:","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"});
        dailyLogs.add(new String[]{"-----------","------","-------","---------","--------","------","--------","------"});
        // stock
        List<String> low = new ArrayList<>();
        low.add("Stock| Low:");
        List<String> out = new ArrayList<>();
        out.add("Stock| Out:");
        // other
        List<String> sign_in = new ArrayList<>();
        sign_in.add("Signed In :");
        List<String> logs = new ArrayList<>();
        logs.add("Logs:");
        // compile days
        for(Day d : weekLog){
            if(d != null) { // day is not empty
                if (!d.getLowStock().isEmpty()) low.add(d.getLowStock());
                else low.add("");
                if (!d.getOutStock().isEmpty()) out.add(d.getOutStock());
                else out.add("");
                if (!d.getSign().isEmpty()) sign_in.add(d.getSign());
                else sign_in.add("");
                if (!d.getLog().isEmpty()) logs.add(d.getLog());
                else logs.add("");
            }else { // empty day
                low.add("");
                out.add("");
                sign_in.add("");
                logs.add("");
            }
        }

        // put days into compiled logs
        dailyLogs.add(low.toArray(new String[0]));
        dailyLogs.add(out.toArray(new String[0]));
        dailyLogs.add(sign_in.toArray(new String[0]));
        dailyLogs.add(logs.toArray(new String[0]));

        return dailyLogs;
    }

    /**
     * Compiles the week's data into a list of String[] for use in a CSVWriter object.
     * @return a list of String[], each String[] represents a row in a CSV file
     */
    List<String[]> getWeekCSV(){

       List<String[]> allLines = new ArrayList<>();
       String[] blankRow = {""};
       // compile full report of week in List<String[]> for a CSV file
       allLines.add(new String[]{"WEEK OF: (YYYY-MM-DD)",startingMonday.toString()});
       allLines.add(new String[]{"---------------------","----------"});
       allLines.add(blankRow);
       allLines.addAll(compileTimeOff());
       allLines.add(blankRow);
       allLines.addAll(compileWaste());
       allLines.add(blankRow);
       allLines.addAll(compileCleaningList());
       allLines.add(blankRow);
       allLines.addAll(compileDailyLogs());

       return allLines;
    }
}
