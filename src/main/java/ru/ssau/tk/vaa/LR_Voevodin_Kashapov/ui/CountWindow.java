package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CountWindow extends JFrame {
    private final JTextField countGet;
    private int count;

    private double[] xValues;
    private double[] yValues;

    public CountWindow() {
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(3,1,2,2));
        cp.add(new JLabel("Введите количество точек"));
        countGet = new JTextField(10);
        cp.add(countGet);
        countGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                count = Integer.parseInt(countGet.getText());
            }
        });


        JButton pointsCreate = new JButton("next");
        pointsCreate.addActionListener(new ButtonEventListener());
        cp.add(pointsCreate);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("First operation");
        setSize(500, 200);
        setVisible(true);
    }

    private class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            new PointsWindow(count);
        }
    }

    public int getCount() {
        return count;
    }
}
