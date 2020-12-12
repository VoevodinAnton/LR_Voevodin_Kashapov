package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JDialog {

    JLabel label = new JLabel("Выберите реализацию функции");
    JButton button = new JButton("ОК");
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
        container.add(button);

        listButton.isSelected();

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
                        .addComponent(arrayButton))
                .addComponent(button)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listButton)
                        .addComponent(arrayButton))
                .addComponent(button)
        );


    }

    private void addButtonListeners() {
        button.addActionListener(evt -> {
            if(evt.getSource() == button){
                if (listButton.isSelected()) {
                    PointsWindow.set(new LinkedListTabulatedFunctionFactory());
                    MathFuncWindow.set(new LinkedListTabulatedFunctionFactory());
                }
                if ( arrayButton.isSelected()){
                    PointsWindow.set(new ArrayTabulatedFunctionFactory());
                    MathFuncWindow.set(new ArrayTabulatedFunctionFactory());
                }
                this.dispose();
            }

        });
    }


}
