package ds;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logbook implements Serializable {
    private Map<LocalDate, WeeklyReport> weeks; // maps startingMonday to report

    public Logbook(){
        weeks = new HashMap<>();
    }
    public Logbook(Logbook l){
        this.weeks = new HashMap<>(l.weeks);
    }

    // returns the starting monday in a week of a given day
    private LocalDate startingMondayOf(LocalDate day){
        DayOfWeek d = day.getDayOfWeek();
        switch (d){
            case MONDAY:
                return day;
            case TUESDAY:
                return day.minusDays(1);
            case WEDNESDAY:
                return day.minusDays(2);
            case THURSDAY:
                return day.minusDays(3);
            case FRIDAY:
                return day.minusDays(4);
            case SATURDAY:
                return day.minusDays(5);
            default:
                return day.minusDays(6);
        }
    }

    // updates the logs of a given date
    public void updateLog(LocalDate toUpdate, Day newContent){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toUpdate);
        // get the weekly report for the given date
        WeeklyReport wr = weeks.get(mondayOfWeek);
        if(wr == null){ // if there is no weekly report, make one and populate it with known data
            wr = new WeeklyReport(mondayOfWeek);
            wr.updateLog(toUpdate, newContent);
            weeks.put(mondayOfWeek, wr);
        }else{ // otherwise, update the retrieved weekly report
            wr.updateLog(toUpdate, newContent);
            weeks.replace(toUpdate, wr);
        }
    }
    public void updateCleaningList(LocalDate toUpdate, List<Task> tasks){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toUpdate);
        // get the weekly report for the given date
        WeeklyReport wr = weeks.get(mondayOfWeek);
        if(wr == null){ // if there is no weekly report, make one and populate it with known data
            wr = new WeeklyReport(mondayOfWeek);
            wr.updateCleaningList(tasks);
            weeks.put(mondayOfWeek, wr);
        }else{ // otherwise, update the retrieved weekly report
            wr.updateCleaningList(tasks);
            weeks.replace(toUpdate, wr);
        }
    }
    public void addWasteReport(LocalDate toUpdate, WasteReport wr){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toUpdate);
        // get the weekly report for the given date
        WeeklyReport weeklyReport = weeks.get(mondayOfWeek);
        if(weeklyReport == null){ // if there is no weekly report, make one and populate it with known data
            weeklyReport = new WeeklyReport(mondayOfWeek);
            weeklyReport.addWasteReport(wr);
            weeks.put(mondayOfWeek, weeklyReport);
        }else{ // otherwise, update the retrieved weekly report
            weeklyReport.addWasteReport(wr);
            weeks.replace(toUpdate, weeklyReport);
        }
    }
    public void addTimeOffRequest(LocalDate toUpdate, TimeRequest tr){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toUpdate);
        // get the weekly report for the given date
        WeeklyReport weeklyReport = weeks.get(mondayOfWeek);
        if(weeklyReport == null){ // if there is no weekly report, make one and populate it with known data
            weeklyReport = new WeeklyReport(mondayOfWeek);
            weeklyReport.addTimeOffRequest(tr);
            weeks.put(mondayOfWeek, weeklyReport);
        }else{ // otherwise, update the retrieved weekly report
            weeklyReport.addTimeOffRequest(tr);
            weeks.replace(toUpdate, weeklyReport);
        }
    }

    public Day getLog(LocalDate toView){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toView);
        // get the weekly report for the given date
        WeeklyReport weeklyReport = weeks.get(mondayOfWeek);
        if(weeklyReport == null){ // if there is no weekly report, make one and populate it with known data
            return null;
        }else{ // otherwise, update the retrieved weekly report
            return weeklyReport.getDayLogs(toView);
        }
    }
    public List<Task> getCleaningList(LocalDate toView){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toView);
        // get the weekly report for the given date
        WeeklyReport weeklyReport = weeks.get(mondayOfWeek);
        if(weeklyReport == null){ // if there is no weekly report, make one and populate it with known data
            return null;
        }else{ // otherwise, update the retrieved weekly report
            return weeklyReport.getCleaningList();
        }
    }
    public List<String[]> getWeekCSV(LocalDate toView){
        // gets the monday of the week this date is in.
        // (weeks are sorted by starting monday)
        LocalDate mondayOfWeek = startingMondayOf(toView);
        // get the weekly report for the given date
        WeeklyReport weeklyReport = weeks.get(mondayOfWeek);
        if(weeklyReport == null){ // if there is no weekly report, make one and populate it with known data
            return null;
        }else{ // otherwise, update the retrieved weekly report
            return weeklyReport.getWeekCSV();
        }
    }

    public void saveToFile(String filename){
        File file = new File(filename);
        try (
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ){
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Logbook loadFromFile(String filename){
        File file = new File(filename);
        if(!file.exists()) return null;
        try (
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ){
            return new Logbook((Logbook)ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
