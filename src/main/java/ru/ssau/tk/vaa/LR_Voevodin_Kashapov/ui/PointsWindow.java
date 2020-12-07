package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class PointsWindow extends JFrame {
    private final int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final AbstractTableModel myTableModel = new MyTableModel(xValues, yValues);
    private final JTable table = new JTable(myTableModel);
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    TabulatedFunction function;

    public PointsWindow(int count) {
        super("coordinates");
        this.count = count;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        Container cp = getContentPane();
        //cp.setLayout(new GridLayout(count, 2, 2, 2));
        cp.add(buttonCreateFunction);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        createTable();

        addButtonListeners();
        compose();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTable() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            yValues.add(i, "");
            myTableModel.fireTableDataChanged();
        }
    }
    private void addButtonListeners() {
        buttonCreateFunction.addActionListener(evt ->{
            if (table.isEditing())
                table.getCellEditor().stopCellEditing();
            double[] x = toArray(xValues);
            double[] y = toArray(yValues);

            function =  new ArrayTabulatedFunctionFactory().create(x,y);
            System.out.println(function.toString());
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
        for(String element: list){
            array[i++] = Double.parseDouble(element);
        }
        return array;
    }



}
