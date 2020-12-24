package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.CompositeFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    private final JMenuItem downloadButton = new JMenuItem("Загрузить");

    //Fields
    private final JTextField countField = new JTextField("");
    private final JTextField numberOfFField = new JTextField("");
    private JTextField select1 = new JTextField("Первая функция (g)");
    private JTextField select2 = new JTextField("Вторая функция (f)");

    //Labels
    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JLabel numberOfFLabel = new JLabel("Количество функций:");
    private final JLabel info = new JLabel("f(g)");
    private final JLabel helpInfo1 = new JLabel("Создайте функцию, подтвердите, выберите f и g, создайте f(g). ");
    private final JLabel helpInfo2 = new JLabel("Чтобы посмотреть или выбрать функцию - выберите её (если вы ее создали) из выпадающего списка.");

    //Box
    private final JComboBox<String> funcBox = new JComboBox<>();
    private final Map<String, TabulatedFunction> selectFunc = new HashMap<>();

    private final JMenuBar menuBar = new JMenuBar();

    JFileChooser downloadChooser = new JFileChooser();

    public CompositeFunctionWindow() {
        setTitle("Сложная функция");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setModal(true);
        compose();
        addButtonListeners();

        downloadChooser.setDialogTitle("Загрузка функции");
        downloadChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        downloadChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        downloadChooser.setCurrentDirectory(new File("output"));


        menuBar.add(createMenuFile());

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
                confirmButton.setEnabled(false);
                funcGet.setEnabled(true);
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

        downloadButton.addActionListener(evt -> {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    selectFunc.put("Функция #" + (numberOfFunctions + 1), FunctionsIO.deserialize(in));
                    numberOfFField.setText(String.valueOf(numberOfFunctions + 1));
                    funcBox.addItem("Функция #" + (numberOfFunctions + 1));
                    numberOfFunctions++;
                    flag1 = false;
                    flag2 = false;
                    select1Button.setEnabled(true);
                    select2Button.setEnabled(true);
                    compositeButton.setEnabled(true);
                } catch (IOException | ClassNotFoundException e) {
                    new ErrorWindow(this, e);
                }
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

    private JMenu createMenuFile() {
        JMenu menu = new JMenu("меню");

        menu.add(downloadButton);

        menuBar.add(menu);
        return menu;
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
                        .addComponent(menuBar)
                        .addGap(1, 10000, Short.MAX_VALUE))
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
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(50)
                                        .addComponent(helpInfo1))
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(50)
                                        .addComponent(helpInfo2))
                                .addComponent(compositeButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(tableScrollPane))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(numberOfFLabel)
                        .addComponent(numberOfFField, 0, GroupLayout.DEFAULT_SIZE, 50)
                        .addComponent(funcBox))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(menuBar)
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
                                        .addComponent(select1, 0, GroupLayout.DEFAULT_SIZE, 40)
                                        .addComponent(select1Button, 0, GroupLayout.DEFAULT_SIZE, 40)
                                        .addComponent(select2Button, 0, GroupLayout.DEFAULT_SIZE, 40)
                                        .addComponent(select2, 0, GroupLayout.DEFAULT_SIZE, 40))
                                .addComponent(helpInfo1)
                                .addComponent(helpInfo2)
                                .addComponent(compositeButton))
                        .addComponent(tableScrollPane))
                .addGroup(layout.createParallelGroup()
                        .addComponent(numberOfFLabel)
                        .addComponent(numberOfFField)
                        .addComponent(funcBox))
        );
    }
}
