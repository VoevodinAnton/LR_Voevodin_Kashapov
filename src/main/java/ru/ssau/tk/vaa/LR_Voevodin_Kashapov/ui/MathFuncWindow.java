package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MathFuncWindow extends JDialog {
    public static TabulatedFunctionFactory factory;
    private JComboBox<String> funcBox = new JComboBox<>();
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    private JLabel xFrom = new JLabel("Начальная точка");
    private JLabel xTo = new JLabel("Конечная точка");
    private JLabel xCount = new JLabel("Количество точек");
    private JTextField xFromField = new JTextField();
    private JTextField xToField = new JTextField();
    private JTextField xCountField = new JTextField();
    private Map<String, MathFunction> selectFunc = new HashMap<>();
    protected TabulatedFunction function;
    private int count;


    public MathFuncWindow(int count) {
        setSize(500, 150);
        setTitle("Function");
        Container cp = getContentPane();
        //cp.setLayout(new GridLayout(count, 2, 2, 2));
        fillMap();
        compose();
        cp.add(buttonCreateFunction);
        this.count = count;
        addButtonListeners();

        setLocationRelativeTo(null);
        setVisible(true);

        }

    public static void set(TabulatedFunctionFactory factory){
        MathFuncWindow.factory = factory;
    }

    public void fillMap() {
        selectFunc.put("Квадратичная функция", new SqrFunction());
        selectFunc.put("Десятая степень", new TenthPowFunction());
        selectFunc.put("Нулевая функция", new ZeroFunction());
        selectFunc.put("Единичная функция", new UnitFunction());
        selectFunc.put("Тождественная функция", new IdentityFunction());
        selectFunc.put("Синусоидальная функция", new SinFunction());

        String[] functions = new String[6];
        int i = 0;
        for (String string : selectFunc.keySet()) {
            functions[i++] = string;
        }
        Arrays.sort(functions);
        for (String string : functions) {
            funcBox.addItem(string);
        }
    }

    public void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(xFrom)
                        .addComponent(xFromField)
                        .addComponent(xTo)
                        .addComponent(xToField))
                .addComponent(funcBox)
                .addComponent(buttonCreateFunction)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(xFrom)
                        .addComponent(xFromField)
                        .addComponent(xTo)
                        .addComponent(xToField))
                .addComponent(funcBox)
                .addComponent(buttonCreateFunction)
        );
    }

    private void addButtonListeners() {
        buttonCreateFunction.addActionListener(evt -> {
            try {
                String func = (String) funcBox.getSelectedItem();
                MathFunction selectedFunction = selectFunc.get(func);
                double xFrom = Double.parseDouble(xFromField.getText());
                double xTo = Double.parseDouble(xToField.getText());
                function = MathFuncWindow.factory.create(selectedFunction, xFrom, xTo, count);
                System.out.println(function.toString());
            } catch (Exception e) {
                new ErrorWindow(this, e);
            }
        });
    }
}