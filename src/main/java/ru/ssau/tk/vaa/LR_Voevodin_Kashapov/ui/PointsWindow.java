package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PointsWindow extends JFrame {
    private final int count;
    private double[] xValues;
    private double[] yValues;
    public PointsWindow(int count) {
        this.count = count;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(count,2,2,2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("coordinates");
        setSize(500, 200);
        setVisible(true);
        JTextField[] xField = new JTextField[count];
        JTextField[] yField = new JTextField[count];
        for (int i = 0; i < count; i++) {
            cp.add(new JLabel("Введите количество точек"));
            xField[i] = new JTextField(10);
            yField[i] = new JTextField(10);
            cp.add(xField[i]);
            cp.add(yField[i]);
            int finalI = i;
            /*xField[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    xValues[finalI] = Integer.parseInt(xField[finalI].getText());
                }
            });*/
        }

    }

}
