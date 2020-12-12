package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JDialog {

    JLabel label = new JLabel("Выберите реализацию функции");
    JButton listButton = new JButton("Связный список");
    JButton arrayButton = new JButton("Массив");


    public SettingsWindow() {
        setSize(300, 150);
        setTitle("Настройки");
        Container container = getContentPane();

        container.setLayout(new FlowLayout(FlowLayout.RIGHT));
        setLocationRelativeTo(null);

        container.add(label);
        container.add(listButton);
        container.add(arrayButton);

        arrayButton.setEnabled(false);

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
