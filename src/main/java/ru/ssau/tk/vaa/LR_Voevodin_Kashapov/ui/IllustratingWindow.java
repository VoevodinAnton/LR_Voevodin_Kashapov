package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io.FunctionsIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
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

    //chooser
    JFileChooser saveChooser = new JFileChooser();
    JFileChooser downloadChooser = new JFileChooser();

    //Buttons
    JButton createTableButton = new JButton("Создать таблицу");
    JButton funcSaveButton = new JButton("Записать функцию");
    JButton downloadButton = new JButton("Загрузить");
    JButton saveButton = new JButton("Сохранить");
    JButton buildButton = new JButton("Построить");
    JButton savePictureButton = new JButton("Сохранить график");

    public IllustratingWindow() {
        setSize(1000, 500);
        setTitle("plot");
        funcSaveButton.setEnabled(false);
        saveButton.setEnabled(false);
        buildButton.setEnabled(false);
        interpolation.setEnabled(false);
        xGet.setEnabled(false);
        interpolationResult.setEnabled(false);
        yGet.setEnabled(false);

        downloadChooser.setDialogTitle("Загрузка функции");
        downloadChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        downloadChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        downloadChooser.setCurrentDirectory(new File("output"));

        saveChooser.setDialogTitle("Сохранение файла");
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveChooser.addChoosableFileFilter(new FileNameExtensionFilter("Bin files", "bin"));
        saveChooser.setCurrentDirectory(new File("output"));

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
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(createTableButton)
                        .addComponent(funcSaveButton))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(tableScrollPane1)
                        .addComponent(chartPanel))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(downloadButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(countLabel)
                        .addComponent(countGet)
                        .addComponent(createTableButton)
                        .addComponent(funcSaveButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane1)
                        .addComponent(chartPanel))
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(downloadButton)
                                .addComponent(saveButton))
                        .addComponent(interpolation)
                        .addComponent(xGet,0, GroupLayout.DEFAULT_SIZE, 30)
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
                yGet.setEnabled(true);
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
        });

        saveButton.addActionListener(evt -> {
            int returnVal = saveChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = saveChooser.getSelectedFile() + ".bin";
                File file = new File(fileName);
                if (file.exists()) {
                    int ind = JOptionPane.showConfirmDialog(this, "Файл с таким названием уже существует в данном расположении. Вы хотите сохранить файл с названием " +
                                    HelperMethods.getFinalNewDestinationFile(new File("output"), file).getName() + "?",
                            "Предупреждение", JOptionPane.YES_NO_OPTION);
                    if (ind == 0) {
                        file = HelperMethods.getFinalNewDestinationFile(new File("output"), file);
                    }

                }
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
                } catch (IOException | ClassNotFoundException e) {
                    new ErrorWindow(this, e);
                }
            }
        });

        xGet.addActionListener(evt ->{
            try {
                double x = Double.parseDouble(xGet.getText());
                double y = function.apply(x);
                yGet.setText(Double.toString(y));
            } catch (Exception exception) {
                new ErrorWindow(this, exception);
            }
        });

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
