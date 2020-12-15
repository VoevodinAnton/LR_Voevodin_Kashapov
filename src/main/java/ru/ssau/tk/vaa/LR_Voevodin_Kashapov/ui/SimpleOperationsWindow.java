package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleOperationsWindow extends JDialog {
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    private final List<String> xValues = new ArrayList<>();
    private final List<String> y1Values = new ArrayList<>();
    private final List<String> y2Values = new ArrayList<>();
    private final List<String> result = new ArrayList<>();
    private final AbstractTableModel myTableModel1 = new MyTableModel(xValues, y1Values);
    private final AbstractTableModel myTableModel2 = new MyTableModel(xValues, y2Values);
    private final AbstractTableModel myTableModel3= new MyTableModel(xValues, result);
    private final JTable table1 = new JTable(myTableModel1);
    private final JTable table2 = new JTable(myTableModel2);
    private final JTable table3 = new JTable(myTableModel3);
    private final JButton operateButton = new JButton("Выполнить");
    private final JButton saveButton = new JButton("Сохранить");
    protected TabulatedFunction function;

    public SimpleOperationsWindow() {
        setSize(500, 300);
        Container cp = getContentPane();
        setTitle("Operations");

        cp.add(operateButton);
        cp.add(saveButton);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        createTable();
        addButtonListeners();
        compose();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTable() {
        for (int i = 0; i < 10; i++) {
            xValues.add(i, "");
            y1Values.add(i, "");
            y2Values.add(i, "");
            result.add(i, "");
            myTableModel1.fireTableDataChanged();
            myTableModel2.fireTableDataChanged();
            myTableModel3.fireTableDataChanged();
        }
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane1 = new JScrollPane(table1);
        JScrollPane tableScrollPane2 = new JScrollPane(table2);
        JScrollPane tableScrollPane3 = new JScrollPane(table3);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2)
                        .addComponent(tableScrollPane3))
                .addComponent(operateButton)
                .addComponent(saveButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2)
                        .addComponent(tableScrollPane3))
                .addComponent(operateButton)
                .addComponent(saveButton)
        );


    }

    private void addButtonListeners() {
        saveButton.addActionListener(evt ->{
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "bin");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
            }
        });
    }
}

