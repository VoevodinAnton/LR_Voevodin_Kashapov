package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GraphicInterface extends JFrame {

    JButton functionCreate = new JButton("Создать функцию");
    JButton simpleOperationsButton = new JButton("Выполнить операцию");
    JButton differentiatingButton = new JButton("Дифференцирование функции");
    ImageIcon imageIcon = new ImageIcon("settingsButton.png");
    String mainImageFilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("main.jpg")).getFile();
    JLabel mainImage = new JLabel(new ImageIcon(mainImageFilePath));
    JButton settingsButton = new JButton("Настройки");

    //String iconFilePath = this.getClass().getClassLoader().getResource("settingsButton.png").getFile();

    public GraphicInterface() {
        super("Main window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 320);
        compose();
        setLocationRelativeTo(null);
        addButtonListeners();
        setVisible(true);

        // не добавляется картинка на кнопку
        //settingsButton.setBounds(30, 30, 100, 100);
        //settingsButton.setBorder(BorderFactory.createEmptyBorder());
        //settingsButton.setContentAreaFilled(false);
        //settingsButton.setFocusable(false);
    }


    private void addButtonListeners() {
        functionCreate.addActionListener(evt -> new CountWindow());

        settingsButton.addActionListener(evt -> new SettingsWindow());

        simpleOperationsButton.addActionListener(evt -> new SimpleOperationsWindow());

        differentiatingButton.addActionListener(evt -> new DifferentiatingWindow());
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainImage))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(settingsButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(functionCreate, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(simpleOperationsButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(differentiatingButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))

        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(mainImage))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(settingsButton)
                                .addComponent(functionCreate)
                                .addComponent(simpleOperationsButton)
                                .addComponent(differentiatingButton)))
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphicInterface::new);
    }
}