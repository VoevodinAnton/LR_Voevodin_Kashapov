package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.operations.*;

import java.io.*;

public class ArrayTabulatedFunctionSerialization {

    public static void main(String[] args) {
        File fileArray = new File("output/serialized array functions.bin");
        double[] xValue = new double[]{1, 2, 3, 4, 5};
        double[] yValue = new double[]{2, 4, 6, 8, 10};

        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValue, yValue);
        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new ArrayTabulatedFunctionFactory());
        TabulatedFunction firstDerivative = differentialOperator.derive(arrayFunction);
        TabulatedFunction SecondDerivative = differentialOperator.derive(firstDerivative);
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileArray));
            FunctionsIO.serialize(out, arrayFunction);
            FunctionsIO.serialize(out, firstDerivative);
            FunctionsIO.serialize(out, SecondDerivative);
            out.close();

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileArray));
            TabulatedFunction deserializedArray = FunctionsIO.deserialize(in);
            TabulatedFunction deserializedFirstDerivative = FunctionsIO.deserialize(in);
            TabulatedFunction deserializedSecondDerivative = FunctionsIO.deserialize(in);

            System.out.println(deserializedArray.toString());
            System.out.println(deserializedFirstDerivative.toString());
            System.out.println(deserializedSecondDerivative.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
