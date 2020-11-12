package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;

import java.io.*;

public class ArrayTabulatedFunctionSerializationJson {
    public static void main(String[] args) {
        File fileArray = new File("output/serialized array functions JSON.txt");

        double[] xValue = new double[]{1, 2, 3, 4, 5};
        double[] yValue = new double[]{2, 4, 6, 8, 10};

        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValue, yValue);

        try(BufferedWriter out = new BufferedWriter(new FileWriter(fileArray))){
            FunctionsIO.serializeJson(out, arrayFunction);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}