package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.*;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {
    public static void main(String[] args) {
        File file = new File("input/function.txt");
        try (BufferedReader inArray = new BufferedReader(new FileReader(file)); BufferedReader inList = new BufferedReader(new FileReader(file))) {

            TabulatedFunction arrayFunction = FunctionsIO.readTabulatedFunction(inArray, new ArrayTabulatedFunctionFactory());
            System.out.println(arrayFunction.toString());

            TabulatedFunction listFunction = FunctionsIO.readTabulatedFunction(inList, new LinkedListTabulatedFunctionFactory());
            System.out.println(listFunction.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
