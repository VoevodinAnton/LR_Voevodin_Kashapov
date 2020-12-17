package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.TabulatedDifferentialOperator;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {

    public static void main(String[] args) {
        File fileList = new File("output/serialized linked list functions.bin");

        double[] xValues = new double[]{0, 2, 3, 4, 5};
        double[] yValues = new double[]{2, 4, 6, 8, 10};

        LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(xValues, yValues);
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction firstDerivative = differentialOperator.derive(list);
        TabulatedFunction secondDerivative = differentialOperator.derive(firstDerivative);

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileList))) {
            FunctionsIO.serialize(out, list);
            FunctionsIO.serialize(out, firstDerivative);
            FunctionsIO.serialize(out, secondDerivative);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileList))) {
            TabulatedFunction deserializedList = FunctionsIO.deserialize(in);
            TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(in);
            TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(in);

            System.out.println(deserializedList.toString());
            System.out.println(deserializedFirstDerivative.toString());
            System.out.println(deserializedSecondDerivative.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
