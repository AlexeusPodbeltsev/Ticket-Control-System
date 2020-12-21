import javax.swing.*;
import java.util.ArrayList;

public class TableCreator {
    public static void createTravelTable(ArrayList<Travel> travelArrayList) {
        String[] columnsHeader = new String[]{"Id", "From", "To",
                "Date", "Time", "Price", "Seats", "Type", "  "};
        Object[][] data = new Object[travelArrayList.size()][columnsHeader.length];
        for (int i = 0; i < travelArrayList.size(); i++) {
            data[i][0] = travelArrayList.get(i).getId();
            data[i][1] = travelArrayList.get(i).getFrom();
            data[i][2] = travelArrayList.get(i).getTo();
            data[i][3] = travelArrayList.get(i).getDate();
            data[i][4] = travelArrayList.get(i).getTime();
            data[i][5] = travelArrayList.get(i).getPrice();
            if (travelArrayList.get(i).getSeats()==0)
                data[i][6] = "Sold Out";
            else
                data[i][6] = travelArrayList.get(i).getSeats();
            data[i][7] = travelArrayList.get(i).getType();
            data[i][8] = "Choose";
        }
        JFrame frame = new JFrame("Trips");
        JTable travelTable = new JTable(data, columnsHeader);
        travelTable.setDefaultEditor(Object.class, null);
        JScrollPane travelTableScrollPane = new JScrollPane(travelTable);
        travelTable.getColumn("  ").setCellRenderer(new JButtonTable.ButtonRenderer());
        travelTable.getColumn("  ").setCellEditor(new JButtonTable.ButtonEditor(new JCheckBox(), travelTable,frame));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(travelTableScrollPane);
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void createTicketTable(ArrayList<Ticket> ticketArrayList) {
        String[] columnsHeader = new String[]{"Id", "Passport","From", "To",
                "Date", "Time", "Seat Number", "Type", "  "};
        Object[][] data = new Object[ticketArrayList.size()][columnsHeader.length];
        for (int i = 0; i < ticketArrayList.size(); i++) {
            data[i][0] = ticketArrayList.get(i).getId();
            data[i][1] = ticketArrayList.get(i).getPassport();
            data[i][2] = ticketArrayList.get(i).getFrom();
            data[i][3] = ticketArrayList.get(i).getTo();
            data[i][4] = ticketArrayList.get(i).getDate();
            data[i][5] = ticketArrayList.get(i).getTime();
            data[i][6] = ticketArrayList.get(i).getSeatNumber();
            data[i][7] = ticketArrayList.get(i).getType();
            data[i][8] = "Delete";

        }
        JFrame frame = new JFrame("Tickets");
        JTable travelTable = new JTable(data, columnsHeader);
        travelTable.setDefaultEditor(Object.class, null);
        JScrollPane travelTableScrollPane = new JScrollPane(travelTable);
        travelTable.getColumn("  ").setCellRenderer(new JButtonTable.ButtonRenderer());
        travelTable.getColumn("  ").setCellEditor(new JButtonTable.ButtonEditor(new JCheckBox(), travelTable,frame));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(travelTableScrollPane);
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}