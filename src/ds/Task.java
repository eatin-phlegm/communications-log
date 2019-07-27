package ds;

import java.io.Serializable;

public class Task implements Serializable {
    private String description;
    private boolean isComplete;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Task(String description, boolean isComplete){
        this.description = description;
        this.isComplete = isComplete;
    }
    public Task(Task t){
        this.description = t.description;
        this.isComplete = t.isComplete;
    }
}
