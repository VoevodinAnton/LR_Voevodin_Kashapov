package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MathFuncWindow extends JDialog {
    private final JComboBox<String> funcBox = new JComboBox<>();

    //Buttons
    private final JButton buttonCreateFunction = new JButton("Создать функцию");
    private final JButton saveButton = new JButton("Сохранить");

    private final JFileChooser saveChooser = new JFileChooser();

    private final JLabel xFrom = new JLabel("Начальная точка");
    private final JLabel xTo = new JLabel("Конечная точка");
    private final JTextField xFromField = new JTextField();
    private final JTextField xToField = new JTextField();
    private final Map<String, MathFunction> selectFunc = new HashMap<>();
    protected TabulatedFunction function;
    private final int count;


    public MathFuncWindow(int count) {
        this.setModal(true);
        setSize(500, 150);
        setTitle("Плавная функция");
        fillMap();
        this.count = count;

        saveChooser.setCurrentDirectory(new File("output"));
        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveButton.setEnabled(false);

        addButtonListeners();
        compose();

        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);

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
                .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addComponent(buttonCreateFunction)
                )
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(xFrom)
                        .addComponent(xFromField)
                        .addComponent(xTo)
                        .addComponent(xToField))
                .addComponent(funcBox)
                .addGroup(layout.createParallelGroup()
                        .addComponent(saveButton)
                        .addComponent(buttonCreateFunction))

        );
    }

    private void addButtonListeners() {
        buttonCreateFunction.addActionListener(evt -> {
            try {
                String func = (String) funcBox.getSelectedItem();
                MathFunction selectedFunction = selectFunc.get(func);
                double xFrom = Double.parseDouble(xFromField.getText());
                double xTo = Double.parseDouble(xToField.getText());
                function = SettingsWindow.factory.create(selectedFunction, xFrom, xTo, count);
                //System.out.println(String.valueOf(function));
                saveButton.setEnabled(true);
                System.out.println(function.toString());
            } catch (Exception e) {
                new ErrorWindow(this, e);
            }
        });

        saveButton.addActionListener(evt -> {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = saveChooser.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием уже существует в данном расположении. Вы хотите сохранить файл с названием " +
                                    HelperMethods.getFinalNewDestinationFile(new File("output"), file).getName() + "?",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        file = HelperMethods.getFinalNewDestinationFile(new File("output"), file);
                        flag = 1;
                    }
                }
                if (flag != -1){
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
    }
}