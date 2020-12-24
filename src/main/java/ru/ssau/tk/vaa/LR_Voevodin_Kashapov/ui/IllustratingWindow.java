package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IllustratingWindow extends JDialog {
    private int count;

    private final JLabel countLabel = new JLabel("Введите количество точек");
    private final JTextField countGet = new JTextField();
    private final JLabel interpolation = new JLabel("x:");
    private final JTextField xGet = new JTextField();
    private final JLabel interpolationResult = new JLabel("y:");
    private final JTextField yGet = new JTextField();


    private final JMenuBar menuBar = new JMenuBar();

    private final java.util.List<String> xValues = new ArrayList<>();
    private final List<String> yValues = new ArrayList<>();
    private final MyTableModel myTableModel = new MyTableModel(xValues, yValues);
    private final JTable table = new JTable(myTableModel);
    protected TabulatedFunction function;


    //plot
    private final XYSeries functionSeries = new XYSeries("function");
    private final XYSeriesCollection dataset = new XYSeriesCollection();
    JFreeChart chart = ChartFactory
            .createXYLineChart("График функции", "x", "y",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, true);

    //choosers
    JFileChooser saveChooser = new JFileChooser();
    JFileChooser downloadChooser = new JFileChooser();
    JFileChooser savePictureChooser = new JFileChooser();

    //Buttons
    JMenuItem downloadButton = new JMenuItem("Загрузить");
    JMenuItem saveButton = new JMenuItem("Сохранить");
    JMenuItem savePictureButton = new JMenuItem("Сохранить график");
    JButton createTableButton = new JButton("Создать таблицу");
    JButton funcSaveButton = new JButton("Записать функцию");
    JButton buildButton = new JButton("Построить");

    public IllustratingWindow() {
        setSize(1100, 600);
        setTitle("plot");
        funcSaveButton.setEnabled(false);
        saveButton.setEnabled(false);
        buildButton.setEnabled(false);
        interpolation.setEnabled(false);
        xGet.setEnabled(false);
        interpolationResult.setEnabled(false);
        yGet.setEnabled(false);
        savePictureButton.setEnabled(false);

        menuBar.add(createMenuFile());

        downloadChooser.setDialogTitle("Загрузка функции");
        downloadChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        downloadChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        downloadChooser.setCurrentDirectory(new File("output"));

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

        savePictureChooser.setDialogTitle("Сохранение графика");
        savePictureChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        savePictureChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG images", "jpeg"));
        savePictureChooser.setCurrentDirectory(new File("pictures"));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addButtonListeners();
        compose();

        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTable() {
        for (int i = 0; i < count; i++) {
            xValues.add(i, "");
            yValues.add(i, "");
            myTableModel.fireTableDataChanged();
        }
    }

    private void compose() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JScrollPane tableScrollPane1 = new JScrollPane(table);
        ChartPanel chartPanel = new ChartPanel(chart);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(menuBar)
                        .addGap(1, 10000, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addGap(60)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(createTableButton)
                        .addComponent(funcSaveButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addComponent(chartPanel))
                .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(interpolation)
                        .addComponent(xGet)
                        .addComponent(interpolationResult)
                        .addComponent(yGet)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buildButton))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(menuBar)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(createTableButton)
                        .addComponent(funcSaveButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addComponent(chartPanel))
                .addGroup(layout.createParallelGroup()
                        .addComponent(interpolation)
                        .addComponent(xGet, 0, GroupLayout.DEFAULT_SIZE, 30)
                        .addComponent(interpolationResult)
                        .addComponent(yGet, 0, GroupLayout.DEFAULT_SIZE, 30)
                        .addComponent(buildButton))
        );
    }

    private void addButtonListeners() {
        createTableButton.addActionListener(evt -> {
            try {
                xValues.clear();
                yValues.clear();
                this.count = Integer.parseInt(countGet.getText());
                if (count < 2) {
                    throw new WrongNumberOfElementsException();
                }

                createTable();
                funcSaveButton.setEnabled(true);
                createTableButton.setEnabled(false);
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        funcSaveButton.addActionListener(evt -> {
            try {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                function = SettingsWindow.factory.create(toArray(xValues), toArray(yValues));
                //System.out.println(function.toString());

                funcSaveButton.setEnabled(false);
                saveButton.setEnabled(true);
                buildButton.setEnabled(true);
                interpolation.setEnabled(true);
                xGet.setEnabled(true);
                interpolationResult.setEnabled(true);
                countGet.setText("");

            } catch (Exception exception) {
                xValues.clear();
                yValues.clear();
                createTable();
                new ErrorWindow(this, exception);
            }
        });

        buildButton.addActionListener(evt -> {
            createDataSet();
            chart.fireChartChanged();
            savePictureButton.setEnabled(true);
        });

        saveButton.addActionListener(evt -> {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = saveChooser.getSelectedFile() + ".bin";
                int flag = 1;
                File file = new File(fileName);
                if (file.exists()) {
                    flag = -1;
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием уже существует в данном расположении. Вы хотите сохранить файл с названием " +
                                    HelperMethods.getFinalNewDestinationFile(new File("output"), file).getName() + "?",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        file = HelperMethods.getFinalNewDestinationFile(new File("output"), file);
                        flag = 1;
                    }
                }
                if (flag != -1){
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                        if (function != null) {
                            FunctionsIO.serialize(out, function);
                            JOptionPane.showMessageDialog(this,
                                    "Файл '" + file.getName() +
                                            " сохранен");
                        } else {
                            throw new IOException();
                        }
                    } catch (Exception e) {
                        new ErrorWindow(this, e);
                    }
                }

            }

        });

        downloadButton.addActionListener(evt -> {
            int returnVal = downloadChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = downloadChooser.getSelectedFile();
                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                    xValues.clear();
                    yValues.clear();
                    myTableModel.fireTableDataChanged();
                    function = FunctionsIO.deserialize(in);
                    count = function.getCount();
                    for (int i = 0; i < count; i++) {
                        xValues.add(i, String.valueOf(function.getX(i)));
                        yValues.add(i, String.valueOf(function.getY(i)));
                        myTableModel.fireTableDataChanged();
                    }

                    {
                        createTableButton.setEnabled(false);
                        countGet.setEnabled(false);
                        countLabel.setEnabled(false);
                        saveButton.setEnabled(true);
                        buildButton.setEnabled(true);
                        interpolation.setEnabled(true);
                        xGet.setEnabled(true);
                        interpolationResult.setEnabled(true);
                        yGet.setEnabled(true);
                        countGet.setText("");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        xGet.addActionListener(evt -> {
            try {
                double x = Double.parseDouble(xGet.getText());
                double y = function.apply(x);
                yGet.setText(Double.toString(y));
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

        savePictureButton.addActionListener(evt -> {
            int width = 640;   /* Width of the image */
            int height = 480;  /* Height of the image */


            int returnVal = savePictureChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = savePictureChooser.getSelectedFile() + ".jpeg";
                File XYChart = new File(fileName);
                if (XYChart.exists()) {
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием уже существует в данном расположении. Вы хотите сохранить файл с названием " +
                                    HelperMethods.getFinalNewDestinationFile(new File("pictures"), XYChart).getName() + "?",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        XYChart = HelperMethods.getFinalNewDestinationFile(new File("pictures"), XYChart);
                    } else {
                        dispose();
                    }
                }
                try {
                    ChartUtilities.saveChartAsJPEG(XYChart, chart, width, height);

                } catch (Exception e) {
                    new ErrorWindow(this, e);
                }
            }

        });

    }

    private JMenu createMenuFile() {
        JMenu menu = new JMenu("меню");

        menu.add(downloadButton);
        menu.add(saveButton);
        menu.add(savePictureButton);

        menuBar.add(menu);
        return menu;
    }

    private void createDataSet() {
        functionSeries.clear();
        dataset.removeAllSeries();
        for (int i = 0; i < count; i++) {
            functionSeries.add(function.getX(i), function.getY(i));
        }
        dataset.addSeries(functionSeries);
    }


    private double[] toArray(List<String> list) {
        double[] array = new double[list.size()];
        int i = 0;
        for (String element : list) {
            array[i++] = Double.parseDouble(element);
        }
        return array;
    }

}
