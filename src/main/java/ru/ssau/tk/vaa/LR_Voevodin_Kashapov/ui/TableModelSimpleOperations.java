package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelSimpleOperations extends AbstractTableModel {
    private static final int X_ROW = 0;
    private static final int Y1_ROW = 1;
    private static final int Y2_ROW = 2;
    private static final int RES_ROW = 3;
    private final List<String> xValues;
    private final List<String> y1Values;
    private final List<String> y2Values;
    private final List<String> result;

    public TableModelSimpleOperations(List<String> xValues, List<String> y1Values, List<String> y2Values, List<String> result) {
        this.xValues = xValues;
        this.y1Values = y1Values;
        this.y2Values = y2Values;
        this.result = result;
    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public int getColumnCount() {
        return xValues.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (rowIndex) {
            case X_ROW:
                return xValues.get(columnIndex);

            case Y1_ROW:
                return y1Values.get(columnIndex);

            case Y2_ROW:
                return y2Values.get(columnIndex);

            case RES_ROW:
                return result.get(columnIndex);
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (rowIndex) {
            case X_ROW -> xValues.set(columnIndex, String.valueOf(value));
            case Y1_ROW -> y1Values.set(columnIndex, String.valueOf(value));
            case Y2_ROW -> y2Values.set(columnIndex, String.valueOf(value));
            case RES_ROW -> result.set(columnIndex, String.valueOf(value));
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case X_ROW -> "X";
            case Y1_ROW -> "Y1";
            case Y2_ROW -> "Y2";
            case RES_ROW -> "Результат";
            default -> super.getColumnName(column);
        };
    }
}
