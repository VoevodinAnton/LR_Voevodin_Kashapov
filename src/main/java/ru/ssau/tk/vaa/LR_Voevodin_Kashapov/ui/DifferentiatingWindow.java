package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.TabulatedDifferentialOperator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;
import java.util.List;

public class DifferentiatingWindow extends JDialog {
    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JTextField countGet = new JTextField(10);
    private int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> xResValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final List<String> result = new ArrayList<>();
    private final MyTableModel myTable0Model = new MyTableModel(xValues, yValues);
    private final MyTableModel myTable1Model = new MyTableModel(xResValues, result);
    private final JTable table0 = new JTable(myTable0Model);
    private final JTable table1 = new JTable(myTable1Model);
    private final JFileChooser saveChooser = new JFileChooser();
    private final JFileChooser downloadChooser = new JFileChooser();

    //Buttons
    private final JButton funcCreate = new JButton("Создать таблицу");
    private final JButton funcSave = new JButton("Записать функцию");
    private final JButton operateButton = new JButton("Продифференцировать");
    private final JButton saveButton0 = new JButton("Сохранить");
    private final JButton saveButton1 = new JButton("Сохранить");
    private final JButton downloadButton0 = new JButton("Загрузить");
    private final JButton clearButton = new JButton("Очистить таблицы");

    private static final TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator();
    protected TabulatedFunction function0;
    protected TabulatedFunction functionD;

    public DifferentiatingWindow() {
        setSize(550, 400);
        setTitle("Дифференцирование функции");
        operateButton.setEnabled(false);
        funcSave.setEnabled(false);
        saveButton0.setEnabled(false);
        saveButton1.setEnabled(false);
        table0.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        downloadChooser.setDialogTitle("Загрузка функции");
        downloadChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        downloadChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        downloadChooser.setCurrentDirectory(new File("output"));

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

        addButtonListeners();
        compose();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTable1() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            yValues.add(i, "");
            myTable0Model.fireTableDataChanged();
        }
        saveButton0.setEnabled(true);
    }

    private void createTable2() {
        xResValues.clear();
        result.clear();
        for (int i = 0; i < count; i++) {
            xResValues.add(i, String.valueOf(functionD.getX(i)));
            result.add(i, String.valueOf(functionD.getY(i)));
            myTable1Model.fireTableDataChanged();
        }
        saveButton1.setEnabled(true);
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
                                .addComponent(saveButton1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clearButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                .addComponent(saveButton1)
                                .addComponent(clearButton)))
                .addComponent(operateButton)
        );
    }

    private void addButtonListeners() {
        funcCreate.addActionListener(evt -> {
            try {
                xValues.clear();
                yValues.clear();
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
                function0 = SettingsWindow.factory.create(toArray(xValues), toArray(yValues));
                //System.out.println(function0.toString());
                funcSave.setEnabled(false);
                operateButton.setEnabled(true);
                countGet.setText("");

            } catch (Exception exception) {
                xValues.clear();
                yValues.clear();
                createTable1();
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

        saveButton0.addActionListener(evt -> {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = saveChooser.getSelectedFile() + ".bin";
                int flag = 0;
                File file = new File(fileName);
                if (file.exists()) {
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием уже существует в данном расположении. Вы хотите сохранить файл с названием " +
                            HelperMethods.getFinalNewDestinationFile(new File("output"), file).getName() + "?" +
                            "\n Нажмите \"Нет\", чтобы заменить файл");
                    switch (ind) {
                        case (0):
                            file = HelperMethods.getFinalNewDestinationFile(new File("output"), file);
                            break;
                        case (2):
                            flag = -1;
                        default:
                            break;
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (function0 != null) {
                            FunctionsIO.serialize(out, function0);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception e) {
                        new ErrorWindow(this, e);
                    }
                }
            }
        });
        saveButton1.addActionListener(evt -> {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = saveChooser.getSelectedFile() + ".bin";
                int flag = 0;
                File file = new File(fileName);
                if (file.exists()) {
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием уже существует в данном расположении. Вы хотите сохранить файл с названием " +
                            HelperMethods.getFinalNewDestinationFile(new File("output"), file).getName() + "?" +
                            "\n Нажмите \"Нет\", чтобы заменить файл");
                    switch (ind) {
                        case (0):
                            file = HelperMethods.getFinalNewDestinationFile(new File("output"), file);
                            break;
                        case (2):
                            flag = -1;
                        default:
                            break;
                    }
                }
                if (flag != -1) {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (functionD != null) {
                            FunctionsIO.serialize(out, functionD);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception e) {
                        new ErrorWindow(this, e);
                    }
                }
            }

        });

        downloadButton0.addActionListener(evt -> {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    xValues.clear();
                    yValues.clear();
                    myTable0Model.fireTableDataChanged();
                    function0 = FunctionsIO.deserialize(in);
                    count = function0.getCount();
                    for (int i = 0; i < count; i++) {
                        xValues.add(i, String.valueOf(function0.getX(i)));
                        yValues.add(i, String.valueOf(function0.getY(i)));
                        myTable0Model.fireTableDataChanged();
                    }
                    funcCreate.setEnabled(false);
                    countGet.setEnabled(false);
                    countLabel.setEnabled(false);
                    funcCreate.setEnabled(false);
                    operateButton.setEnabled(true);
                    saveButton0.setEnabled(true);
                } catch (IOException | ClassNotFoundException e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        clearButton.addActionListener(evt -> {
            xValues.clear();
            xResValues.clear();
            yValues.clear();
            result.clear();
            myTable0Model.fireTableDataChanged();
            myTable1Model.fireTableDataChanged();
            countGet.setEnabled(true);
            countGet.setText("");
            funcCreate.setEnabled(true);
            funcSave.setEnabled(false);
            downloadButton0.setEnabled(true);
            saveButton1.setEnabled(false);
            saveButton0.setEnabled(false);
            operateButton.setEnabled(false);
        });
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
