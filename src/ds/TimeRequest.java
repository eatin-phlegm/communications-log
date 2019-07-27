package ds;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeRequest implements Serializable {
    private LocalDate timeRequested;
    private LocalDate desiredStart, desiredEnd;
    private String employee, reason;

    public TimeRequest(String employee, String reason, LocalDate timeStamp, LocalDate desiredStart, LocalDate desiredEnd){
        this.employee = employee;
        this.reason = reason;
        this.timeRequested = timeStamp;
        this.desiredStart = desiredStart;
        this.desiredEnd = desiredEnd;
    }

    public LocalDate getTimeRequested() {
        return timeRequested;
    }

    public LocalDate getDesiredStart() {
        return desiredStart;
    }

    public LocalDate getDesiredEnd() {
        return desiredEnd;
    }

    public String getEmployee() {
        return employee;
    }

    public String getReason() {
        return reason;
    }
}
