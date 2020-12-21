import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PassengerTicketCreator {
    private static JFrame frame;
    private static ArrayList<Passenger> passengers = new ArrayList();

    private static void panelCreator(JFrame jframe, JTable table) {

        JButton add_passenger = new JButton("Add Passenger");
        JButton remove = new JButton("Remove");
        JButton buyButton = new JButton("Buy");
        JButton confirm = new JButton("Confirm");
        JButton edit = new JButton("Edit");


        TextField name = new TextField(12);
        TextField surname = new TextField(12);
        TextField passport = new TextField(12);


        Label namel = new Label("Name: ");
        Label surnamel = new Label("Surname: ");
        Label passportl = new Label("Passport: ");

        JPanel panel = new JPanel();

        panel.setBackground(Color.darkGray);
        panel.add(namel);
        panel.add(name);
        panel.add(surnamel);
        panel.add(surname);
        panel.add(passportl);
        panel.add(passport);
        panel.add(confirm);
        panel.add(remove);

        jframe.getContentPane().setLayout(
                new BoxLayout(jframe.getContentPane(), BoxLayout.PAGE_AXIS)
        );
        jframe.setBackground(Color.DARK_GRAY);
        jframe.add(panel);
        jframe.add(add_passenger);
        jframe.add(buyButton);
        jframe.pack();


        confirm.addActionListener(e -> {
            if (passport.getText().matches("^\\d{10}$")){
                if (name.getText().equals("") || surname.getText().equals("")) {
                    JOptionPane.showMessageDialog(confirm, "Fields Name and Surname should be filled");
                }else {
                    if (passengers.isEmpty()){
                        Passenger passenger = new Passenger(passport.getText().trim(),
                                name.getText(), surname.getText());
                        passengers.add(passenger);
                        passport.setEditable(false);
                        name.setEditable(false);
                        surname.setEditable(false);

                        panel.remove(remove);
                        panel.remove(confirm);
                        panel.add(edit);
                        panel.add(remove);
                        jframe.pack();
                    }else {
                        int k = 0;
                        for (Passenger value : passengers) {
                            if (passport.getText().equals(
                                    String.valueOf(value.getPassport()))) {
                                JOptionPane.showMessageDialog(confirm, "Passenger with Passport number: " + passport.getText()
                                        + "\n already added");
                                k++;
                            }
                        }
                        if (k==0){
                                Passenger passenger = new Passenger(passport.getText().trim(),
                                        name.getText(), surname.getText());
                                passengers.add(passenger);
                                passport.setEditable(false);
                                name.setEditable(false);
                                surname.setEditable(false);

                                panel.remove(remove);
                                panel.remove(confirm);
                                panel.add(edit);
                                panel.add(remove);
                                jframe.pack();
                        }
                        }
                    }
        }else JOptionPane.showMessageDialog(confirm,"Passport should has 10 numbers.");
        });

        edit.addActionListener(e -> {
            for (int i = 0; i < passengers.size(); i++) {
                if (passport.getText().equals(String.valueOf(passengers.get(i).getPassport()))
                        && name.getText().equals(passengers.get(i).getName())
                        && surname.getText().equals(passengers.get(i).getSurname())) {
                    passengers.remove(i);
                }
            }
            passport.setEditable(true);
            name.setEditable(true);
            surname.setEditable(true);

            panel.remove(remove);
            panel.remove(edit);
            panel.add(confirm);
            panel.add(remove);
            jframe.pack();
        });


        add_passenger.addActionListener(e -> {
                    jframe.remove(buyButton);
                    jframe.remove(add_passenger);
                    panelCreator(jframe, table);
                }
        );

        remove.addActionListener(e -> {
            jframe.remove(panel);
            jframe.pack();
                for (int i = 0; i < passengers.size(); i++) {
                    if (passport.getText().equals(String.valueOf(passengers.get(i).getPassport()))
                            && name.getText().equals(passengers.get(i).getName())
                            && surname.getText().equals(passengers.get(i).getSurname())) {
                        passengers.remove(i);
                    }
                }

        }
        );


        buyButton.addActionListener(e ->{
            ConnectToDataBase.createTicket(
                (int)table.getValueAt(table.getSelectedRow(),0),passengers);
            int result = JOptionPane.showConfirmDialog(buyButton,
                    "Buying Success!\n Would you like to continue buying?","",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION)Window.setGUI();
            if(result == JOptionPane.NO_OPTION)System.exit(0);
            jframe.dispose();
        passengers.removeAll(passengers);});

    }

    public static void createPassenger(JTable table) {
        frame = new JFrame("Buy Ticket");
        panelCreator(frame, table);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}