package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.TabulatedFunctionOperationService;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;
import java.util.List;

public class DifferentiatingWindow extends JDialog {
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JTextField countGet = new JTextField(10);
    private int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> xResValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final List<String> result = new ArrayList<>();
    private final AbstractTableModel myTable0Model = new MyTableModel(xValues, yValues);
    private final AbstractTableModel myTable1Model = new MyTableModel(xResValues, result);
    private final JTable table0 = new JTable(myTable0Model);
    private final JTable table1 = new JTable(myTable1Model);
    JFileChooser chooser = new JFileChooser();

    //Buttons
    private final JButton funcCreate = new JButton("Создать таблицу");
    private final JButton funcSave = new JButton("Записать функцию");
    private final JButton operateButton = new JButton("Продифференцировать");
    private final JButton saveButton0 = new JButton("Сохранить");
    private final JButton saveButton1 = new JButton("Сохранить");
    private final JButton downloadButton0 = new JButton("Загрузить");
    private final JButton downloadButton1 = new JButton("Загрузить");

    private static final TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator();
    protected TabulatedFunction function0;
    protected TabulatedFunction functionD;

    public DifferentiatingWindow() {
        setSize(550, 400);
        setTitle("Дифференцирование функции");
        operateButton.setEnabled(false);
        funcSave.setEnabled(false);
        table0.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table1.setCellSelectionEnabled(false);
        table1.setEnabled(false);
        table1.setDragEnabled(false);
        addButtonListeners();
        compose();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void set(TabulatedFunctionFactory factory) {
        DifferentiatingWindow.factory = factory;
        differentialOperator.setFactory(factory);
    }

    private void createTable1() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            yValues.add(i, "");
            myTable0Model.fireTableDataChanged();
        }
    }

    private void createTable2() {
        for (int i = 0; i < count; i++) {
            double x = functionD.getX(i);
            double y = functionD.getY(i);
            xResValues.add(i, String.valueOf(x));
            result.add(i, String.valueOf(y));
            myTable1Model.fireTableDataChanged();
        }
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane1 = new JScrollPane(table0);
        JScrollPane tableScrollPane2 = new JScrollPane(table1);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate)
                        .addComponent(funcSave))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(downloadButton0, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton0, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(saveButton1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(operateButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate)
                        .addComponent(funcSave))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2))
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(downloadButton0)
                                .addComponent(saveButton0))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton1))
                )
                .addComponent(operateButton)
        );
    }

    private void addButtonListeners() {
        funcCreate.addActionListener(evt -> {
            try {
                this.count = Integer.parseInt(countGet.getText());
                if (count < 2) {
                    throw new WrongNumberOfElementsException();
                }
                createTable1();
                funcSave.setEnabled(true);
                funcCreate.setEnabled(false);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
        funcSave.addActionListener(evt -> {
            try {
                if (table0.isEditing()) {
                    table0.getCellEditor().stopCellEditing();
                }
                double[] x = toArray(xValues);
                double[] y = toArray(yValues);
                function0 = SimpleOperationsWindow.factory.create(x, y);
                System.out.println(function0.toString());
                funcSave.setEnabled(false);
                operateButton.setEnabled(true);
                table0.setEnabled(false);

            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
        operateButton.addActionListener(evt -> {
            try {
                functionD = differentialOperator.derive(function0);
                createTable2();
                saveButton1.setEnabled(true);

            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        saveButton0.addActionListener(evt -> {});
        saveButton1.addActionListener(evt -> {});
        downloadButton1.addActionListener(evt -> {});
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
