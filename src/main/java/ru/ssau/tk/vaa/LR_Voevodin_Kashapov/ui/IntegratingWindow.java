package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.IntegratingTask;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class IntegratingWindow extends JDialog {

    //Variables
    private int count;
    private int threadCount = 1;
    private double integral;
    protected TabulatedFunction function0;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();

    //Labels
    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JLabel threadLabel = new JLabel("Введите количество потоков");
    private final JLabel integralLabel = new JLabel("Интеграл равен:");

    //TextFields
    private final JTextField countGet = new JTextField();
    private final JTextField threadGet = new JTextField();
    private final JTextField integralResult = new JTextField();

    //Files
    private final JFileChooser saveChooser = new JFileChooser();
    private final JFileChooser downloadChooser = new JFileChooser();

    //Buttons
    private final JButton funcCreate = new JButton("Создать таблицу");
    private final JButton funcSave = new JButton("Записать функцию");
    private final JButton operateButton = new JButton("Проинтегрировать");
    private final JButton saveButton0 = new JButton("Сохранить");
    private final JButton downloadButton0 = new JButton("Загрузить");
    private final JButton clearButton = new JButton("Очистить всё");

    //Tables
    private final MyTableModel myTable0Model = new MyTableModel(xValues, yValues);
    private final JTable table0 = new JTable(myTable0Model);

    public IntegratingWindow() {
        setSize(600, 450);
        setTitle("Интегрирование функции");
        operateButton.setEnabled(false);
        funcSave.setEnabled(false);
        saveButton0.setEnabled(false);
        table0.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        downloadChooser.setDialogTitle("Загрузка функции");
        downloadChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        downloadChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        downloadChooser.setCurrentDirectory(new File("output"));

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

        integralResult.setEnabled(false);
        addButtonListeners();
        compose();
        setLocationRelativeTo(null);
        setModal(true);
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

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane1 = new JScrollPane(table0);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(countLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(countGet, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(funcCreate, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(funcSave, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(threadLabel)
                                .addComponent(threadGet)))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(downloadButton0, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton0, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clearButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(operateButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(integralLabel)
                        .addComponent(integralResult))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate)
                        .addComponent(funcSave))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(threadLabel)
                                .addComponent(threadGet, 0, GroupLayout.PREFERRED_SIZE, 40)))
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(downloadButton0)
                                .addComponent(saveButton0)
                                .addComponent(clearButton)))
                .addComponent(operateButton)
                .addGroup(layout.createParallelGroup()
                        .addComponent(integralLabel)
                        .addComponent(integralResult))
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
                threadCount = Integer.parseInt(threadGet.getText());
                threadGet.setEnabled(false);
                List<Double> integratingResult = new ArrayList<>();
                double sum = 0;
                ForkJoinPool pool = new ForkJoinPool(threadCount);

                IntegratingTask task = new IntegratingTask(function0, function0.leftBound(), function0.rightBound(), integratingResult);
                pool.invoke(task);

                for (double s : integratingResult) {
                    sum = sum + s;
                }

                integral = sum;
                integralResult.setText(String.valueOf(integral));
                //System.out.println(integral);
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
            yValues.clear();
            myTable0Model.fireTableDataChanged();
            countGet.setEnabled(true);
            countGet.setText("");
            funcCreate.setEnabled(true);
            funcSave.setEnabled(false);
            downloadButton0.setEnabled(true);
            saveButton0.setEnabled(false);
            operateButton.setEnabled(false);
            integralResult.setText("");
            threadGet.setText("");
            threadGet.setEnabled(true);
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
