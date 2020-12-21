import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class JButtonTable {


    static class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
        static class ButtonEditor extends DefaultCellEditor {

            protected JButton button;
            private String label;
            private boolean isPushed;

            public ButtonEditor(JCheckBox checkBox, JTable table,JFrame frame) {
                super(checkBox);
                button = new JButton();
                button.setOpaque(true);

                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                if (stackTrace[2].getMethodName().equals("createTravelTable")) {

                    button.addActionListener(e -> {
                        String isSoldOut = String.valueOf(table.getValueAt(table.getSelectedRow(), 6));
                        if (isSoldOut.equals("Sold Out")) {
                            JOptionPane.showMessageDialog(button, "Sorry, but all tickets are sold out");
                        } else {
                            PassengerTicketCreator.createPassenger(table);
                            frame.dispose();
                        }
                    });
                }
                if (stackTrace[2].getMethodName().equals("createTicketTable")) {

                    button.addActionListener(e ->{
                            ConnectToDataBase.deleteTicket((int)table.getValueAt(table.getSelectedRow(), 0),button);
                        frame.dispose();
                            ConnectToDataBase.getSearchingTickets((String) table.getValueAt(table.getSelectedRow(), 1),button,frame);
                    });
                }

                }



            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                if (isSelected) {
                    button.setForeground(table.getSelectionForeground());
                    button.setBackground(table.getSelectionBackground());
                } else {
                    button.setForeground(table.getForeground());
                    button.setBackground(table.getBackground());
                }
                label = (value == null) ? "" : value.toString();
                button.setText(label);
                isPushed = true;
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                if (isPushed) {
                    button.setText("I've been clicked");
                }
                isPushed = false;
                return label;
            }

            @Override
            public boolean stopCellEditing() {
                isPushed = false;
                return super.stopCellEditing();
            }
        }
    }