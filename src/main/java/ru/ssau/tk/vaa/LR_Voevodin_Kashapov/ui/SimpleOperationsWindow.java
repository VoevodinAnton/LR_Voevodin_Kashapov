package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.FunctionAreNotSimilarException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.TabulatedFunctionOperationService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.*;
import java.util.List;

public class SimpleOperationsWindow extends JDialog {
    //Lists
    private List<String> xValues = new ArrayList<>();
    private List<String> xResValues = new ArrayList<>();
    private List<String> y1Values = new ArrayList<>();
    private List<String> y2Values = new ArrayList<>();
    private List<String> result = new ArrayList<>();

    //Tables
    private XYYTableModel myTableXYYModel = new XYYTableModel(xValues, y1Values, y2Values);
    private MyTableModel myTableResModel = new MyTableModel(xResValues, result);
    private JTable table0 = new JTable(myTableXYYModel);
    private JTable table1 = new JTable(myTableResModel);
    private JScrollPane tableScrollPane1 = new JScrollPane(table0);
    private JScrollPane tableScrollPane2 = new JScrollPane(table1);

    //Labels&TextFields
    private JTextField countGet = new JTextField(10);
    private final JLabel countLabel = new JLabel("Введите количество точек");

    //Buttons
    private final JButton funcCreate = new JButton("Создать таблицу");
    private final JButton funcRealise = new JButton("Записать функции");
    private final JButton operateButton = new JButton("Выполнить");
    private final JButton saveButton1 = new JButton("Сохранить");
    private final JButton saveButton2 = new JButton("Сохранить");
    private final JButton saveButton3 = new JButton("Сохранить");
    private final JButton downloadButton1 = new JButton("Загрузить");
    private final JButton downloadButton2 = new JButton("Загрузить");
    private final JButton clearTableButton = new JButton("Очистить таблицы");

    //Choosers
    JFileChooser downloadChooser = new JFileChooser();
    JFileChooser saveChooser = new JFileChooser();

    //Other
    private final Map<String, TabulatedFunction> selectOperation = new HashMap<>();
    private final JComboBox<String> operationsBox = new JComboBox<>();
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    private int count;
    protected TabulatedFunction function1;
    protected TabulatedFunction function2;
    protected TabulatedFunction function3;

    public SimpleOperationsWindow() {
        setSize(1100, 600);
        setTitle("Операции");
        operateButton.setEnabled(false);
        operationsBox.setEnabled(false);
        funcRealise.setEnabled(false);
        saveButton1.setEnabled(false);
        saveButton2.setEnabled(false);
        saveButton3.setEnabled(false);
        downloadButton2.setEnabled(false);

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

    public static void set(TabulatedFunctionFactory factory) {
        SimpleOperationsWindow.factory = factory;
    }

    private void createTable1() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            y1Values.add(i, "");
            y2Values.add(i, "");
            myTableXYYModel.fireTableDataChanged();
        }
        saveButton1.setEnabled(true);
        saveButton2.setEnabled(true);
    }

    private void createTable2() {
        if (xResValues.isEmpty()) {
            for (int i = 0; i < count; i++) {
                xResValues.add(i, String.valueOf(function3.getX(i)));
                result.add(i, String.valueOf(function3.getY(i)));
                myTableResModel.fireTableDataChanged();
            }
        } else {
            for (int i = 0; i < count; i++) {
                xResValues.set(i, String.valueOf(function3.getX(i)));
                result.set(i, String.valueOf(function3.getY(i)));
                myTableResModel.fireTableDataChanged();
            }
        }
        myTableResModel.fireTableDataChanged();
        saveButton3.setEnabled(true);
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate)
                        .addComponent(funcRealise))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(downloadButton1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(downloadButton2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(saveButton3, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(operationsBox)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(operateButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearTableButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate)
                        .addComponent(funcRealise))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2))
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(downloadButton1)
                                .addComponent(saveButton1))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(downloadButton2)
                                .addComponent(saveButton2))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton3))
                )
                .addComponent(operationsBox)
                .addGroup(layout.createParallelGroup()
                        .addComponent(operateButton)
                        .addComponent(clearTableButton))
        );
    }

    private void addButtonListeners() {
        funcCreate.addActionListener(evt -> {
            try {
                this.count = Integer.parseInt(countGet.getText());
                if (count < 2) {
                    throw new WrongNumberOfElementsException();
                }
                countGet.setText("");
                countGet.setEnabled(false);
                createTable1();
                funcRealise.setEnabled(true);
                funcCreate.setEnabled(false);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        funcRealise.addActionListener(evt -> {
            try {
                if (table0.isEditing()) {
                    table0.getCellEditor().stopCellEditing();
                }
                double[] x = toArray(xValues);
                double[] y1 = toArray(y1Values);
                double[] y2 = toArray(y2Values);
                function1 = SimpleOperationsWindow.factory.create(x, y1);
                function2 = SimpleOperationsWindow.factory.create(x, y2);
                funcRealise.setEnabled(false);
                operateButton.setEnabled(true);
                operationsBox.setEnabled(true);
                if (selectOperation.isEmpty()) {
                    fillMap();
                }
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        operateButton.addActionListener(evt -> {
            try {
                String func = (String) operationsBox.getSelectedItem();
                function3 = selectOperation.get(func);
                createTable2();
                saveButton3.setEnabled(true);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        saveButton1.addActionListener(evt -> {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = new File(saveChooser.getSelectedFile() + ".bin");
                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                    if (function1 != null) {
                        FunctionsIO.serialize(out, function1);
                        JOptionPane.showMessageDialog(this,
                                "Файл '" + saveChooser.getSelectedFile() +
                                        ".bin' сохранен");
                    } else {
                        throw new IOException();
                    }
                } catch (IOException e) {
                    new ErrorWindow(this, e);
                }

            }
        });
        downloadButton1.addActionListener(evt -> {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    xValues.clear();
                    y1Values.clear();
                    y2Values.clear();
                    myTableXYYModel.fireTableDataChanged();
                    function1 = FunctionsIO.deserialize(in);
                    count = function1.getCount();
                    for (int i = 0; i < count; i++) {
                        xValues.add(i, String.valueOf(function1.getX(i)));
                        y1Values.add(i, String.valueOf(function1.getY(i)));
                        y2Values.add(i, "");
                        myTableXYYModel.fireTableDataChanged();
                    }
                    funcCreate.setEnabled(false);
                    funcRealise.setEnabled(true);
                    countGet.setEnabled(false);
                    countLabel.setEnabled(false);
                    funcCreate.setEnabled(false);
                    saveButton1.setEnabled(true);
                    downloadButton1.setEnabled(false);
                    downloadButton2.setEnabled(true);
                } catch (IOException | ClassNotFoundException e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        saveButton2.addActionListener(evt ->

        {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = new File(saveChooser.getSelectedFile() + ".bin");
                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                    if (function2 != null) {
                        FunctionsIO.serialize(out, function2);
                        JOptionPane.showMessageDialog(this,
                                "Файл '" + saveChooser.getSelectedFile() +
                                        ".bin' сохранен");
                    } else {
                        throw new IOException();
                    }
                } catch (IOException e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        downloadButton2.addActionListener(evt ->

        {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    function2 = FunctionsIO.deserialize(in);
                } catch (IOException | ClassNotFoundException e) {
                    new ErrorWindow(this, e);
                }
                try {
                    if (function1.similar(function2)) {
                        for (int i = 0; i < count; i++) {
                            y2Values.add(i, String.valueOf(function2.getY(i)));
                            myTableXYYModel.fireTableDataChanged();
                        }
                        if (selectOperation.isEmpty()) {
                            fillMap();
                        }
                        funcCreate.setEnabled(false);
                        operateButton.setEnabled(true);
                        operationsBox.setEnabled(true);
                        saveButton2.setEnabled(true);
                        downloadButton2.setEnabled(false);
                    } else {
                        throw new FunctionAreNotSimilarException();
                    }
                } catch (Exception e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        saveButton3.addActionListener(evt ->

        {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = new File(saveChooser.getSelectedFile() + ".bin");
                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                    if (function3 != null) {
                        FunctionsIO.serialize(out, function3);
                        JOptionPane.showMessageDialog(this,
                                "Файл '" + saveChooser.getSelectedFile() +
                                        ".bin' сохранен");
                    } else {
                        throw new IOException();
                    }
                } catch (IOException e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        clearTableButton.addActionListener(evt ->

        {
            clearTables();
            operateButton.setEnabled(false);
            operationsBox.setEnabled(false);
            funcRealise.setEnabled(false);
            saveButton1.setEnabled(false);
            saveButton2.setEnabled(false);
            saveButton3.setEnabled(false);
            downloadButton1.setEnabled(true);
            downloadButton2.setEnabled(false);
            countGet.setEnabled(true);
            funcCreate.setEnabled(true);
        });
    }

    public void clearTables() {
        xValues.clear();
        xResValues.clear();
        y1Values.clear();
        y2Values.clear();
        result.clear();
        myTableXYYModel.fireTableDataChanged();
        myTableResModel.fireTableDataChanged();
    }

    public void fillMap() {
        selectOperation.clear();
        selectOperation.put("Сложение функций", new TabulatedFunctionOperationService().add(function1, function2));
        selectOperation.put("Вычитание функций", new TabulatedFunctionOperationService().subtract(function1, function2));
        selectOperation.put("Произведение функций", new TabulatedFunctionOperationService().multiply(function1, function2));
        selectOperation.put("Деление функций", new TabulatedFunctionOperationService().divide(function1, function2));

        String[] functions = new String[4];
        int i = 0;
        for (String string : selectOperation.keySet()) {
            functions[i++] = string;
        }
        Arrays.sort(functions);
        for (String string : functions) {
            operationsBox.addItem(string);
        }
    }

    private double[] toArray(List<String> list) {
        double[] array = new double[list.size()];
        int i = 0;
        for (String element : list) {
            array[i++] = Double.parseDouble(element);
        }
        return array;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleOperationsWindow::new);
    }
}
