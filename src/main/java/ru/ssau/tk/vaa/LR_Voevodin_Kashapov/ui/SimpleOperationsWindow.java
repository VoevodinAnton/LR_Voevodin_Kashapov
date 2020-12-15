package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SimpleOperationsWindow extends JDialog {
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    //private final int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> y1Values = new ArrayList<>();
    private final List<String> y2Values = new ArrayList<>();
    private final List<String> result = new ArrayList<>();
    private final AbstractTableModel myTableModel = new TableModelSimpleOperations(xValues, y1Values, y2Values, result);
    private final JTable table = new JTable(myTableModel);
    private final JButton operateButton = new JButton("Выполнить");
    private final JButton saveButton = new JButton("Сохранить");
    protected TabulatedFunction function;

    public SimpleOperationsWindow() {
        //this.count = count;
        setSize(500, 200);
        Container cp = getContentPane();
        setTitle("Operations");

        cp.add(operateButton);
        cp.add(saveButton);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
            myTableModel.fireTableDataChanged();
        }
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tableScrollPane)
                .addComponent(operateButton)
                .addComponent(saveButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(tableScrollPane)
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

