package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.TabulatedFunctionOperationService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.List;

public class SimpleOperationsWindow extends JDialog {
    public static TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JTextField countGet = new JTextField(10);
    private final JButton funcCreate = new JButton("Создать таблицу");
    private final JButton funcSave = new JButton("Сохранить функции");
    private int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> xResValues = new ArrayList<>();
    private final List<String> y1Values = new ArrayList<>();
    private final List<String> y2Values = new ArrayList<>();
    private final List<String> result = new ArrayList<>();
    private final AbstractTableModel myTableXYYModel = new XYYTableModel(xValues, y1Values, y2Values);
    private final AbstractTableModel myTableResModel = new MyTableModel(xResValues, result);
    private final JTable table0 = new JTable(myTableXYYModel);
    private final JTable table1 = new JTable(myTableResModel);
    private final JButton operateButton = new JButton("Выполнить");
    private final JButton saveButton = new JButton("Сохранить");
    private final Map<String, TabulatedFunction> selectOperation = new HashMap<>();
    private final JComboBox<String> operationsBox = new JComboBox<>();
    protected TabulatedFunction function1;
    protected TabulatedFunction function2;
    protected TabulatedFunction function3;

    public SimpleOperationsWindow() {
        setSize(600, 300);
        setTitle("Операции");
        operateButton.setEnabled(false);
        saveButton.setEnabled(false);
        operationsBox.setEnabled(false);
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
        PointsWindow.factory = factory;
    }

    private void createTable1() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            y1Values.add(i, "");
            y2Values.add(i, "");
            myTableXYYModel.fireTableDataChanged();
        }
    }

    private void createTable2() {
        for (int i = 0; i < count; i++) {
            double x = function1.getX(i);
            double y = function3.getY(i);
            xResValues.add(i, String.valueOf(x));
            result.add(i, String.valueOf(y));
            myTableResModel.fireTableDataChanged();
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
                .addComponent(operationsBox)
                .addComponent(operateButton)
                .addComponent(saveButton)
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
                .addComponent(operationsBox)
                .addComponent(operateButton)
                .addComponent(saveButton)
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
                double[] y1 = toArray(y1Values);
                double[] y2 = toArray(y2Values);
                function1 = SimpleOperationsWindow.factory.create(x, y1);
                function2 = SimpleOperationsWindow.factory.create(x, y2);
                System.out.println(function1.toString());
                System.out.println(function2.toString());
                funcSave.setEnabled(false);
                operateButton.setEnabled(true);
                table0.setEnabled(false);
                operationsBox.setEnabled(true);

            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
            fillMap();
        });
        operateButton.addActionListener(evt -> {
            try {
                String func = (String) operationsBox.getSelectedItem();
                function3 = selectOperation.get(func);
                System.out.println(function3.toString());
                createTable2();
                saveButton.setEnabled(true);

            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
        saveButton.addActionListener(evt -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "txt", "bin");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
            }
        });
    }

    public void fillMap() {
        selectOperation.put("Сложение функций", new TabulatedFunctionOperationService().add(function1, function2));
        selectOperation.put("Вычитание функций", new TabulatedFunctionOperationService().subtract(function1, function2));
        selectOperation.put("Произвдение фунций", new TabulatedFunctionOperationService().multiply(function1, function2));
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
}

