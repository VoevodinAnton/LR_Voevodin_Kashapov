package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;

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
            try {
                int count = Integer.parseInt(countGet.getText());
                if (count < 2) {
                    throw new WrongNumberOfElementsException();
                }
                this.dispose();
                new PointsWindow(count);
            } catch(Exception exception) {
                ErrorWindow errorWindow = new ErrorWindow(this, exception);
                errorWindow.getErrorWindow(this, exception);
            }
        });
    }
}
