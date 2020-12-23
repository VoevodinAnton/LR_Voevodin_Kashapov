package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;

import java.awt.event.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PointsWindow extends JDialog {
    private final int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final MyTableModel myTableModel = new MyTableModel(xValues, yValues);
    private final JTable table = new JTable(myTableModel);
    protected TabulatedFunction function;

    JFileChooser saveChooser = new JFileChooser();

    //Buttons
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    private final JButton saveButton = new JButton("Сохранить");
    private final JButton clearButton = new JButton("Ввести заново");
    private final JButton okButton = new JButton("Готово");

    public PointsWindow(int count) {
        this.count = count;
        setSize(500, 200);
        setTitle("Точки");

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        createTable();

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

        saveButton.setEnabled(false);
        okButton.setVisible(false);
        okButton.setEnabled(false);

        addButtonListeners();
        compose();

        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public PointsWindow(int count, boolean flag) {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.count = count;
        if (flag) {
            boolean happy = true;
        }

        setSize(400, 200);
        setTitle("Точки");

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        createTable();

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

        saveButton.setEnabled(false);
        saveButton.setVisible(false);
        okButton.setVisible(true);
        okButton.setEnabled(false);

        addButtonListeners();
        compose();

        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public PointsWindow(TabulatedFunction function) {
        this.count = function.getCount();
        setSize(300, 250);
        setTitle("Точки");

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

        saveButton.setEnabled(false);
        saveButton.setVisible(false);
        clearButton.setVisible(false);
        clearButton.setEnabled(false);
        buttonCreateFunction.setEnabled(false);
        buttonCreateFunction.setVisible(false);
        okButton.setVisible(true);
        okButton.setEnabled(true);

        addButtonListeners();
        compose();

        for (int i = 0; i < count; i++) {
            xValues.add(i, String.valueOf(function.getX(i)));
            yValues.add(i, String.valueOf(function.getY(i)));
            myTableModel.fireTableDataChanged();
        }

        setModal(true);
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
        buttonCreateFunction.addActionListener(evt -> {
            try {
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                function = SettingsWindow.factory.create(toArray(xValues), toArray(yValues));
                saveButton.setEnabled(true);
                okButton.setEnabled(true);
            } catch (Exception exception) {
                removeElements();
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
                        function = SettingsWindow.factory.create(toArray(xValues), toArray(yValues));
                        System.out.println(function.toString());
                        saveButton.setEnabled(true);
                    } catch (Exception exception) {
                        ErrorWindow errorWindow = new ErrorWindow(PointsWindow.this, exception);
                        errorWindow.getErrorWindow(PointsWindow.this, exception);
                    }

                }

            }
        });

        saveButton.addActionListener(evt -> {
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
                        if (function != null) {
                            FunctionsIO.serialize(out, function);
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

        clearButton.addActionListener(evt -> {
            myTableModel.removeAll();
            createTable();
        });

        okButton.addActionListener(evt -> {
            if (function != null) {
                CompositeFunctionWindow.setFunctions(function);
            }
            this.dispose();
        });
    }

    private void removeElements() {
        xValues.clear();
        yValues.clear();
        createTable();
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tableScrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonCreateFunction)
                        .addComponent(saveButton)
                        .addComponent(clearButton)
                        .addComponent(okButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(tableScrollPane)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(buttonCreateFunction)
                        .addComponent(saveButton)
                        .addComponent(clearButton)
                        .addComponent(okButton))
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
