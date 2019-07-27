package ds;

import java.io.Serializable;

public class Day implements Serializable {
    private String log, sign, lowStock, outStock;

    public Day(String log, String sign, String lowStock, String outStock){
        this.log = log;
        this.sign = sign;
        this.lowStock = lowStock;
        this.outStock = outStock;
    }
    public Day(Day d){
        this.log = d.log;
        this.sign = d.sign;
        this.lowStock = d.lowStock;
        this.outStock = d.outStock;
    }

    public String getLog() {
        return log;
    }

    public String getSign() {
        return sign;
    }

    public String getLowStock() {
        return lowStock;
    }

    public String getOutStock() {
        return outStock;
    }
}
