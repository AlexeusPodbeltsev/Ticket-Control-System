package AdditionalClasses;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TravelTableModel extends AbstractTableModel {
    private ArrayList<Object[]> travels;
    private String[] columnsHeader = new String[]{"Id", "From", "To",
            "Date", "Time", "Price", "Seats", "Type", "Button"};

    public TravelTableModel() {
        this.travels = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return travels.size();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return columnsHeader[0];
            case 1:
                return columnsHeader[1];
            case 2:
                return columnsHeader[2];
            case 3:
                return columnsHeader[3];
            case 4:
                return columnsHeader[4];
            case 5:
                return columnsHeader[5];
            case 6:
                return columnsHeader[6];
            case 7:
                return columnsHeader[7];
            case 8:
                return columnsHeader[8];
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return columnsHeader.length;
    }

    public void addData(Object[] row) {
        travels.add(row);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] rows = travels.get(rowIndex);
        return rows[columnIndex];
    }
}