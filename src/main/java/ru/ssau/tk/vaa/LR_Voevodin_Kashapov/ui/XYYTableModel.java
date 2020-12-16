package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class XYYTableModel extends AbstractTableModel {
    private static final int X_COLUMN = 0;
    private static final int Y1_COLUMN = 1;
    private static final int Y2_COLUMN = 2;
    private static final long serialVersionUID = -443916866115057318L;
    private final List<String> xValues;
    private final List<String> y1Values;
    private final List<String> y2Values;


    public XYYTableModel(List<String> xValues, List<String> y1Values, List<String> y2Values) {
        this.xValues = xValues;
        this.y1Values = y1Values;
        this.y2Values = y2Values;
    }

    @Override
    public int getRowCount() {
        return xValues.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case X_COLUMN:
                return xValues.get(rowIndex);

            case Y1_COLUMN:
                return y1Values.get(rowIndex);

            case Y2_COLUMN:
                return y2Values.get(rowIndex);

        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case X_COLUMN -> xValues.set(rowIndex, String.valueOf(value));
            case Y1_COLUMN -> y1Values.set(rowIndex, String.valueOf(value));
            case Y2_COLUMN -> y2Values.set(rowIndex, String.valueOf(value));
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case X_COLUMN -> "X";
            case Y1_COLUMN -> "Y1";
            case Y2_COLUMN -> "Y2";
            default -> super.getColumnName(column);
        };
    }

}


