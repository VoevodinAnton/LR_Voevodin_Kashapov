package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class PointsWindow extends JDialog {
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    private final int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final AbstractTableModel myTableModel = new MyTableModel(xValues, yValues);
    private final JTable table = new JTable(myTableModel);
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    protected TabulatedFunction function;

    public PointsWindow(int count) {
        this.count = count;
        setSize(500, 200);
        Container cp = getContentPane();
        setTitle("coordinates");
        cp.add(buttonCreateFunction);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        createTable();

        addButtonListeners();
        compose();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void set(TabulatedFunctionFactory factory) {
        PointsWindow.factory = factory;
    }

    private void createTable() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            yValues.add(i, "");
            myTableModel.fireTableDataChanged();
        }
    }

    private void addButtonListeners() {
        buttonCreateFunction.addActionListener(evt -> {
            try {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                double[] x = toArray(xValues);
                double[] y = toArray(yValues);
                function = PointsWindow.factory.create(x, y);
                System.out.println(function.toString());

            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        if (table.isEditing())
                            table.getCellEditor().stopCellEditing();
                        double[] x = toArray(xValues);
                        double[] y = toArray(yValues);
                        function = PointsWindow.factory.create(x, y);
                        System.out.println(function.toString());

                    } catch (Exception exception) {
                        ErrorWindow errorWindow = new ErrorWindow(PointsWindow.this, exception);
                        errorWindow.getErrorWindow(PointsWindow.this, exception);
                    }

                }

            }
        });
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tableScrollPane)
                .addComponent(buttonCreateFunction)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(tableScrollPane)
                .addComponent(buttonCreateFunction)
        );


    }

    private double[] toArray(List<String> list) {
        double[] array = new double[list.size()];
        int i = 0;
        for (String element : list) {
            array[i++] = Double.parseDouble(element);
        }
        return array;
    }
}
