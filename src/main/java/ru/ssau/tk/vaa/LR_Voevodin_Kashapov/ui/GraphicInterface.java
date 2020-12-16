package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import javax.swing.*;
import java.awt.*;

public class GraphicInterface extends JFrame {

    JButton functionCreate = new JButton("Создать функцию");
    JButton simpleOperationsButton = new JButton("Выполнить операцию");
    ImageIcon imageIcon = new ImageIcon("settingsButton.png");
    JButton settingsButton = new JButton("Настройки");

    //String iconFilePath = this.getClass().getClassLoader().getResource("settingsButton.png").getFile();

    public GraphicInterface() {
        super("Main window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 80);
        Container container = getContentPane();

        // не добавляется картинка на кнопку
        //settingsButton.setBounds(30, 30, 100, 100);
        //settingsButton.setBorder(BorderFactory.createEmptyBorder());
        //settingsButton.setContentAreaFilled(false);
        //settingsButton.setFocusable(false);


        container.setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        container.add(functionCreate);
        container.add(settingsButton);
        container.add(simpleOperationsButton);

        addButtonListeners();
        setVisible(true);
    }


    private void addButtonListeners() {
        functionCreate.addActionListener(evt -> new CountWindow());

        settingsButton.addActionListener(evt -> new SettingsWindow());

        simpleOperationsButton.addActionListener(evt -> new SimpleOperationsWindow());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphicInterface::new);
    }
}