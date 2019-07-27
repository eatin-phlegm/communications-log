package ds;

import java.io.Serializable;
import java.time.LocalDate;

public class WasteReport implements Serializable {
    private String wasteAmount, item;
    private LocalDate timeSubmitted;

    public WasteReport(String item, String wasteAmount, LocalDate timeStamp){
        this.item = item;
        this.wasteAmount = wasteAmount;
        this.timeSubmitted = timeStamp;
    }
    public WasteReport(WasteReport w){
        this.wasteAmount = w.wasteAmount;
        this.item = w.item;
        this.timeSubmitted = w.timeSubmitted;
    }

    public String getWasteAmount() {
        return wasteAmount;
    }

    public String getItem() {
        return item;
    }

    public LocalDate getTimeSubmitted() {
        return timeSubmitted;
    }

}
