package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import javax.swing.*;
import java.util.Objects;

public class GraphicInterface extends JFrame {

    private final JButton functionCreate = new JButton("Создать функцию");
    private final JButton simpleOperationsButton = new JButton("Выполнить операцию");
    private final JButton differentiatingButton = new JButton("Дифференцирование функции");
    private final JButton integratingButton = new JButton("Интегрирование функции");
    //ImageIcon imageIcon = new ImageIcon("settingsButton.png");
    private final String mainImageFilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("main.jpg")).getFile();
    private final JLabel mainImage = new JLabel(new ImageIcon(mainImageFilePath));
    private final JButton settingsButton = new JButton("Настройки");

    //String iconFilePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("settingsButton.png")).getFile();
    //JLabel setImage = new JLabel(new ImageIcon(iconFilePath));

    public GraphicInterface() {
        super("Главное окно");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 320);
        compose();
        setLocationRelativeTo(null);
        addButtonListeners();
        setVisible(true);

        System.out.println(mainImageFilePath);
        // не добавляется картинка на кнопку
        /*settingsButton.setBounds(30, 30, 100, 100);
        settingsButton.setBorder(BorderFactory.createEmptyBorder());
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusable(false);*/
    }


    private void addButtonListeners() {
        functionCreate.addActionListener(evt -> new CountWindow());

        settingsButton.addActionListener(evt -> new SettingsWindow());

        simpleOperationsButton.addActionListener(evt -> new SimpleOperationsWindow());

        differentiatingButton.addActionListener(evt -> new DifferentiatingWindow());

        integratingButton.addActionListener(evt -> new IntegratingWindow());
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
                                .addComponent(differentiatingButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(integratingButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(mainImage))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(settingsButton)
                                .addComponent(functionCreate)
                                .addComponent(simpleOperationsButton)
                                .addComponent(differentiatingButton)
                                .addComponent(integratingButton)))
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphicInterface::new);
    }
}