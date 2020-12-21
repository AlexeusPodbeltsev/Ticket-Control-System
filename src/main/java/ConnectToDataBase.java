import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ConnectToDataBase {
//    private static final String URL = "jdbc:mysql://139.9.119.34:3306/travelling_2018380254?useSSL=false&serverTimezone=Europe/Moscow";
//    private static final String LOGIN = "s2018380254";
//    private static final String PASSWORD = "GaussDB@123";
    private static final String URL = "jdbc:mysql://localhost:3306/Travelling?useSSL=false&serverTimezone=Europe/Moscow";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "qwertyuiop";
    private static final String SELECT_ALL_TRAVELS = "SELECT* FROM TRAVEL WHERE TravelFrom=? AND TravelTo=? AND TravelDate=?";
    private static final String SELECT_ALL_TICKETS = "select idTicket,Passport, TravelFrom, TravelTo, TravelDate, TravelTime, SeatNumber, Type " +
            "from Ticket join Travel on Travel.idTravel = Ticket.idTravel where passport=?";
    private static final String INSERT_PASSENGER = "INSERT INTO PASSENGER (passport, name, surname) " +
            "VALUES(?,?,?)";
    private static final String INSERT_TICKET = "INSERT INTO TICKET (passport, idTravel, SeatNumber) " +
            "VALUES(?,?,?)";
    private static String DELETE_TICKET = "DELETE FROM TICKET WHERE idTicket=?";
    private static Connection connection = null;
    private static PreparedStatement statement = null;

    private static void connectToDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void getSearchingTravels(String inFrom, String inTo, Date inDate, JButton button, JFrame frame) {
        connectToDataBase();
        ArrayList<Travel> travels = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.prepareStatement(SELECT_ALL_TRAVELS);
            statement.setString(1, inFrom);
            statement.setString(2, inTo);
            statement.setDate(3, new java.sql.Date(inDate.getTime()));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String from = resultSet.getString(2);
                String to = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                Time time = resultSet.getTime(5);
                double price = resultSet.getDouble(6);
                int seats = resultSet.getInt(7);
                String type = resultSet.getString(8);
                Travel travel = new Travel(id, from, to, date, time, price, seats, type);
                travels.add(travel);
            }
            if (travels.isEmpty()) {
                JOptionPane.showMessageDialog(button, "No Trips are found on this route");
            } else {
                TableCreator.createTravelTable(travels);
                frame.dispose();
                travels.removeAll(travels);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTicket(int travelID, ArrayList<Passenger> passengers) {
        connectToDataBase();
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            for (Passenger passenger : passengers) {
                statement = connection.prepareStatement("SELECT* FROM PASSENGER WHERE passport=?");
                statement.setString(1, passenger.getPassport());
                ResultSet checkPassenger = statement.executeQuery();
                if (!checkPassenger.next()) {
                    statement = connection.prepareStatement(INSERT_PASSENGER);
                    statement.setString(1, passenger.getPassport());
                    statement.setString(2, passenger.getName().toUpperCase());
                    statement.setString(3, passenger.getSurname().toUpperCase());
                    statement.executeUpdate();
                }
                statement = connection.prepareStatement("SELECT* FROM TRAVEL WHERE idTravel=?");
                statement.setInt(1, travelID);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {

                    int seats = resultSet.getInt("seats");

                    if (seats > 0) {

                        statement = connection.prepareStatement("SELECT SeatNumber FROM TICKET WHERE idTravel=? ORDER BY SeatNumber");
                        statement.setInt(1, travelID);
                        ResultSet seatNumberSet = statement.executeQuery();

                        int seatNumber = 1;

                        while (seatNumberSet.next()) {
                            seats = seatNumberSet.getInt("SeatNumber");
                            if (seatNumber == seats)
                                seatNumber++;
                            else break;
                        }
                        statement = connection.prepareStatement(INSERT_TICKET);

                        statement.setString(1, passenger.getPassport());
                        statement.setInt(2, travelID);
                        statement.setInt(3, seatNumber);
                        statement.execute();

                        connection.commit();

                    } else
                        connection.rollback();
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getSearchingTickets(String passport, JButton button, JFrame frame) {
        connectToDataBase();
        ArrayList<Ticket> tickets = new ArrayList<>();
        if (passport.matches("^\\d{10}$")) {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                statement = connection.prepareStatement(SELECT_ALL_TICKETS);
                statement.setString(1, passport);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String from = resultSet.getString(3);
                    String to = resultSet.getString(4);
                    Date date = resultSet.getDate(5);
                    Time time = resultSet.getTime(6);
                    int seats = resultSet.getInt(7);
                    String type = resultSet.getString(8);
                    Ticket ticket = new Ticket(id, passport, from, to, date, time, seats, type);
                    tickets.add(ticket);
                }
                if (tickets.isEmpty()) {
                    JOptionPane.showMessageDialog(button, "This passenger has not any tickets");
                    frame.dispose();
                    Window.setGUI();
                } else {
                    frame.dispose();
                    TableCreator.createTicketTable(tickets);
                }
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        } else {
            JOptionPane.showMessageDialog(button, "Passport should has 10 numbers");

        }
    }

    public static void deleteTicket(int id, JButton button){
        connectToDataBase();

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connection.prepareStatement(DELETE_TICKET);
            statement.setInt(1, id);
            statement.executeUpdate();
            int result = JOptionPane.showConfirmDialog(button,"Are you sure?","Confirmation",JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) connection.commit();
            else if (result == JOptionPane.CANCEL_OPTION) connection.rollback();
                connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}