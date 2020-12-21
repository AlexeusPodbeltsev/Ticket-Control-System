import java.sql.Time;
import java.util.Date;

public class Ticket {
    private int id;
    private String passport;
    private String from;
    private String to;
    private Date date;
    private Time time;
    private int seatNumber;
    private String type;

    public Ticket(int id, String passport, String from, String to,
                  Date date, Time time, int seat, String type) {
        this.id = id;
        this.passport = passport;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.seatNumber = seat;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getPassport() {
        return passport;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", passport=" + passport +
                ", from='" + from +
                ", to='" + to +
                ", date=" + date +
                ", time=" + time +
                ", seat number=" + seatNumber +
                ", type='" + type ;
    }

    public String getType() {
        return type;
    }
}
