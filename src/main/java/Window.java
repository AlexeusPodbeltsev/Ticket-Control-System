import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Window {

    public static void setGUI() {

        JFrame frame = new JFrame("Travel");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton checkInfo = new JButton("Check Info");
        JButton findTripButton = new JButton("Find trip");
        TextField from = new TextField(12);
        TextField to = new TextField(12);
        Label fromL = new Label("From: ", Label.RIGHT);
        Label toL = new Label("To: ", Label.RIGHT);
        JXDatePicker picker = new JXDatePicker();
        picker.setDate(Calendar.getInstance().getTime());
        picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.add(fromL);
        panel.add(from);
        panel.add(toL);
        panel.add(to);
        panel.add(picker);
        panel.add(findTripButton);
        frame.getContentPane().add(BorderLayout.SOUTH,panel);
        frame.add(checkInfo);
        checkInfo.addActionListener(e -> {
            frame.dispose();
            JFrame frame1 = new JFrame();
            Label passportl = new Label("Passport: ", Label.RIGHT);
            TextField passport = new TextField(12);
            JPanel p1 = new JPanel();
            JButton findTicketButton = new JButton("Find");
            p1.add(passportl);
            p1.add(passport);
            p1.add(findTicketButton);
            frame1.add(p1);
            frame1.pack();
            frame1.setVisible(true);


            findTicketButton.addActionListener(e1 ->
                ConnectToDataBase.getSearchingTickets(passport.getText(),findTicketButton,frame1));
            frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        });

        findTripButton.addActionListener(e -> ConnectToDataBase.getSearchingTravels(from.getText(),
                to.getText(), picker.getDate(),findTripButton,frame)
        );

        frame.pack();
        frame.setVisible(true);
    }
}
