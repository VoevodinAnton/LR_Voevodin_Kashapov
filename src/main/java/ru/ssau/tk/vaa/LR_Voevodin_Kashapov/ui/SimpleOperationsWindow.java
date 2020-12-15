package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.InconsistentFunctionsExceptions;
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
    private final JButton funcCreate = new JButton("Создать функции");
    private int count;
    private final List<String> xValues1 = new ArrayList<>();
    private final List<String> xValues2 = new ArrayList<>();
    private final List<String> y1Values = new ArrayList<>();
    private final List<String> y2Values = new ArrayList<>();
    private final List<String> result = new ArrayList<>();
    private final AbstractTableModel myTableModel1 = new MyTableModel(xValues1, y1Values);
    private final AbstractTableModel myTableModel2 = new MyTableModel(xValues2, y2Values);
    private final AbstractTableModel myTableModel3= new MyTableModel(xValues1, result);
    private final JTable table1 = new JTable(myTableModel1);
    private final JTable table2 = new JTable(myTableModel2);
    private final JTable table3 = new JTable(myTableModel3);
    private final JButton operateButton = new JButton("Выполнить");
    private final JButton saveButton = new JButton("Сохранить");
    private final TabulatedFunctionOperationService operation = new TabulatedFunctionOperationService();
    private final Map<String, TabulatedFunctionOperationService> selectOperation = new HashMap<>();
    private final JComboBox<String> operationsBox = new JComboBox<>();
    protected TabulatedFunction function1;
    protected TabulatedFunction function2;
    protected TabulatedFunction function3;

    public SimpleOperationsWindow() {
        setSize(500, 300);
        setTitle("Операции");
        operateButton.setEnabled(false);
        saveButton.setEnabled(false);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table1.setName("Первая функция");

        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table2.setName("Вторая функция");

        table3.setCellSelectionEnabled(false);
        table3.setEnabled(false);
        table3.setDragEnabled(false);
        table3.setName("Результирующая функция");
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
            xValues1.add(i, "");
            xValues2.add(i, "");
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
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2)
                        .addComponent(tableScrollPane3))
                .addComponent(operateButton)
                .addComponent(saveButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(funcCreate))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addComponent(tableScrollPane2)
                        .addComponent(tableScrollPane3))
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
                createTable();
                operateButton.setEnabled(true);
                saveButton.setEnabled(true);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
        operateButton.addActionListener(evt -> {
            try {
                if (table1.isEditing() && table2.isEditing()) {
                    table1.getCellEditor().stopCellEditing();
                    table2.getCellEditor().stopCellEditing();
                }
                double[] x1 = toArray(xValues1);
                double[] x2 = toArray(xValues2);
                if (x1 != x2)
                    throw new InconsistentFunctionsExceptions();
                double[] y1 = toArray(y1Values);
                double[] y2 = toArray(y2Values);
                function1 = SimpleOperationsWindow.factory.create(x1, y1);
                function2 = SimpleOperationsWindow.factory.create(x2, y2);
                System.out.println(function1.toString());
                System.out.println(function2.toString());

            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
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

   /* public void fillMap() {
        selectOperation.put("Сложение функций", new add());
        selectOperation.put("Вычитание функций", new TenthPowFunction());
        selectOperation.put("Произвдение фунций", new ZeroFunction());
        selectOperation.put("Деление функций", new UnitFunction());

        String[] functions = new String[6];
        int i = 0;
        for (String string : selectOperation.keySet()) {
            functions[i++] = string;
        }
        Arrays.sort(functions);
        for (String string : functions) {
            operationsBox.addItem(string);
        }
    }*/

    private double[] toArray(List<String> list) {
        double[] array = new double[list.size()];
        int i = 0;
        for (String element : list) {
            array[i++] = Double.parseDouble(element);
        }
        return array;
    }
}

