import java.sql.Time;
import java.util.Date;

public class Travel {
    private int id;
    private String from;
    private String to;
    private Date date;
    private Time time;
    private double price;
    private int seats;
    private String type;


    public Travel(int id, String from, String to, Date date, Time time,
                  double price, int seats, String type) {
        this.id =id;
        this.from =from;
        this.to =to;
        this.date =date;
        this.time =time;
        this.price =price;
        this.seats =seats;
        this.type =type;

    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public Time getTime() {
        return time;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }
}