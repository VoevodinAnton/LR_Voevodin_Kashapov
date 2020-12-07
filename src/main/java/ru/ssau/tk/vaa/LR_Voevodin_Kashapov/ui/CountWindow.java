package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CountWindow extends JFrame {
    JLabel countLabel = new JLabel("Введите количество точек");
    JTextField countGet = new JTextField(10);
    JButton pointsCreate = new JButton("Создать таблицу");


    private double[] xValues;
    private double[] yValues;

    public CountWindow() {
        super("First operation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        Container cp = getContentPane();

        cp.setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        cp.add(countLabel);
        cp.add(countGet);
        cp.add(pointsCreate);

        addButtonListeners();
        setVisible(true);
    }

    private void addButtonListeners() {
        pointsCreate.addActionListener(evt -> {
            int count = Integer.parseInt(countGet.getText());
            this.dispose();
            new PointsWindow(count);
        });
    }
}
