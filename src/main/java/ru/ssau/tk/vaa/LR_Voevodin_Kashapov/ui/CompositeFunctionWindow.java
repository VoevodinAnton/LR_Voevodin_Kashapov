package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.CompositeFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeFunctionWindow extends JDialog {

    //Variables
    private int count;
    private final List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    public static TabulatedFunction function;
    private TabulatedFunction f;
    private TabulatedFunction g;
    private TabulatedFunction fg;
    private int numberOfFunctions = 0;
    private PointsWindow points;
    private boolean flag1 = false;
    private boolean flag2 = false;

    //Tables
    private final MyTableModel myTableModel = new MyTableModel(xValues, yValues);
    private final JTable table = new JTable(myTableModel);


    //Buttons
    private final JButton funcGet = new JButton("Создать новую функцию");
    private final JButton funcLook = new JButton("Посмотреть функцию");
    private final JButton confirmButton = new JButton("Подтвердить");
    private final JButton select1Button = new JButton("Выбрать g");
    private final JButton select2Button = new JButton("Выбрать f");
    private final JButton compositeButton = new JButton("Создать сложную функцию");
    private final JButton clearButton = new JButton("Очистить все");

    //Fields
    private final JTextField countField = new JTextField("");
    private final JTextField numberOfFField = new JTextField("");
    private JTextField select1 = new JTextField("Первая функция (g)");
    private JTextField select2 = new JTextField("Вторая функция (f)");

    //Labels
    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JLabel numberOfFLabel = new JLabel("Количество функций:");
    private final JLabel info = new JLabel("f(g)");

    //Box
    private final JComboBox<String> funcBox = new JComboBox<>();
    private final Map<String, TabulatedFunction> selectFunc = new HashMap<>();

    public CompositeFunctionWindow() {
        setTitle("Сложная функция");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setModal(true);
        compose();
        addButtonListeners();
        numberOfFField.setEnabled(false);
        confirmButton.setEnabled(false);
        select1.setEnabled(false);
        select2.setEnabled(false);
        select1Button.setEnabled(false);
        select2Button.setEnabled(false);
        compositeButton.setEnabled(false);

        setVisible(true);
    }

    private void addButtonListeners() {
        funcGet.addActionListener(evt -> {
            try {
                this.count = Integer.parseInt(countField.getText());
                if (count < 2) {
                    throw new WrongNumberOfElementsException();
                }
                points = new PointsWindow(count, true);
                funcGet.setEnabled(false);
                confirmButton.setEnabled(true);
                countField.setEnabled(false);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        funcLook.addActionListener(evt -> {
            try {
                String func = (String) funcBox.getSelectedItem();
                TabulatedFunction functionS = selectFunc.get(func);
                points = new PointsWindow(functionS);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        confirmButton.addActionListener(evt -> {
            try {
                if (function == null) {
                    throw new NumberFormatException();
                }
                selectFunc.put("Функция #" + (numberOfFunctions + 1), function);
                numberOfFField.setText(String.valueOf(numberOfFunctions + 1));
                funcBox.addItem("Функция #" + (numberOfFunctions + 1));
                numberOfFunctions++;
                confirmButton.setEnabled(false);
                funcGet.setEnabled(true);
                if (!flag1) {
                    select2Button.setEnabled(true);
                }
                if (!flag2) {
                    select1Button.setEnabled(true);
                }
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        select1Button.addActionListener(evt -> {
            try {
                String func = (String) funcBox.getSelectedItem();
                g = selectFunc.get(func);
                select1Button.setEnabled(false);
                select1.setText("g = " + func);

                flag1 = true;
                if (flag2)
                    compositeButton.setEnabled(true);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        select2Button.addActionListener(evt -> {
            try {
                String func = (String) funcBox.getSelectedItem();
                f = selectFunc.get(func);
                select2Button.setEnabled(false);
                select2.setText("f = " + func);

                flag2 = true;

                if (flag1)
                    compositeButton.setEnabled(true);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
        compositeButton.addActionListener(evt -> {
            try {
                double[] x = new double[g.getCount()];
                double[] y = new double[g.getCount()];
                for (int i = 0; i < g.getCount(); i++) {
                    x[i] = g.getX(i);
                    y[i] = new CompositeFunction(g, f).apply(x[i]);
                }
                fg = SettingsWindow.factory.create(x, y);
                selectFunc.put("Функция #" + (numberOfFunctions + 1), fg);
                numberOfFField.setText(String.valueOf(numberOfFunctions + 1));
                funcBox.addItem("Функция #" + (numberOfFunctions + 1));
                numberOfFunctions++;
                createTable();
                compositeButton.setEnabled(false);
                flag1 = false;
                flag2 = false;
                select1Button.setEnabled(true);
                select2Button.setEnabled(true);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        clearButton.addActionListener(evt -> {
            try {
                xValues.clear();
                yValues.clear();
                myTableModel.fireTableDataChanged();
                selectFunc.clear();
                numberOfFField.setText("");
                countField.setText("");
                countField.setEnabled(true);
                funcBox.removeAllItems();
                numberOfFunctions = 0;
                compositeButton.setEnabled(false);
                flag1 = false;
                flag2 = false;
                funcGet.setEnabled(true);
                confirmButton.setEnabled(false);
                select1Button.setEnabled(false);
                select2Button.setEnabled(false);
                select1 = new JTextField("Первая функция (g)");
                select2 = new JTextField("Вторая функция (f)");
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
    }

    private void createTable() {
        xValues.clear();
        yValues.clear();
        for (int i = 0; i < count; i++) {
            xValues.add(i, String.valueOf(fg.getX(i)));
            yValues.add(i, String.valueOf(fg.getY(i)));
            myTableModel.fireTableDataChanged();
        }
    }


    public static void setFunctions(TabulatedFunction function0) {
        function = function0;
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane = new JScrollPane(table);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(countLabel)
                        .addComponent(countField, 0, GroupLayout.DEFAULT_SIZE, 50)
                        .addComponent(clearButton))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(funcGet)
                                        .addComponent(funcLook)
                                        .addComponent(confirmButton))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(225)
                                        .addComponent(info))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(select1)
                                        .addComponent(select1Button)
                                        .addComponent(select2Button)
                                        .addComponent(select2))
                                .addComponent(compositeButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(tableScrollPane))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(numberOfFLabel)
                        .addComponent(numberOfFField, 0, GroupLayout.DEFAULT_SIZE, 50)
                        .addComponent(funcBox))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(countLabel)
                        .addComponent(countField)
                        .addComponent(clearButton))
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(funcGet)
                                        .addComponent(funcLook)
                                        .addComponent(confirmButton))
                                .addComponent(info)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(select1)
                                        .addComponent(select1Button)
                                        .addComponent(select2Button)
                                        .addComponent(select2))
                                .addComponent(compositeButton))
                        .addComponent(tableScrollPane))
                .addGroup(layout.createParallelGroup()
                        .addComponent(numberOfFLabel)
                        .addComponent(numberOfFField)
                        .addComponent(funcBox))
        );
    }
}
