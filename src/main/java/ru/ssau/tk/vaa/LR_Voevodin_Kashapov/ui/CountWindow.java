package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;

import java.awt.*;
import javax.swing.*;

public class CountWindow extends JDialog{
    JLabel countLabel = new JLabel("Введите количество точек");
    JTextField countGet = new JTextField(10);
    JButton pointsCreate = new JButton("Табличная функция");
    JButton mathCreate = new JButton("Плавная функция");

    public CountWindow() {
        setSize(400, 150);
        Container cp = getContentPane();

        cp.setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        cp.add(countLabel);
        cp.add(countGet);
        cp.add(pointsCreate);
        cp.add(mathCreate);

        addButtonListeners();
        setModal(true);
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
                new ErrorWindow(this, exception);
            }
        });

        mathCreate.addActionListener(evt -> {
            try {
                int count = Integer.parseInt(countGet.getText());
                if (count < 2) {
                    throw new WrongNumberOfElementsException();
                }
                this.dispose();
                new MathFuncWindow(count);
            } catch(Exception exception) {
                new ErrorWindow(this, exception);
            }
        });
    }
}
