package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JDialog {

    JLabel label = new JLabel("Выберите реализацию функции");
    JRadioButton listButton = new JRadioButton("Связный список");
    JRadioButton arrayButton = new JRadioButton("Массив");


    public SettingsWindow() {
        setSize(300, 150);
        setTitle("Настройки");
        Container container = getContentPane();

        container.setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        ButtonGroup group = new ButtonGroup();
        group.add(listButton);
        group.add(arrayButton);

        container.add(label);
        container.add(listButton);
        container.add(arrayButton);

        arrayButton.doClick();

        addButtonListeners();
        compose();

        setModal(true);
        setVisible(true);
    }


    public void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(label)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(listButton)
                        .addComponent(arrayButton)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listButton)
                        .addComponent(arrayButton))
        );


    }

    private void addButtonListeners() {
        listButton.addActionListener(evt -> {
            PointsWindow.set(new LinkedListTabulatedFunctionFactory());
            listButton.setEnabled(false);
            arrayButton.setEnabled(true);

        });

        arrayButton.addActionListener(evt -> {
            PointsWindow.set(new ArrayTabulatedFunctionFactory());
            listButton.setEnabled(true);
            arrayButton.setEnabled(false);
        });
    }


}
