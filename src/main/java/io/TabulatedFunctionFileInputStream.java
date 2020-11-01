package io;

import operations.TabulatedDifferentialOperator;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.ArrayTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.*;

import static java.lang.System.in;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args) {
        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream("input/binary function.bin"))){
            ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
            TabulatedFunction functionArray = FunctionsIO.readTabulatedFunction(in, factory);

            System.out.println(functionArray.toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        /*
        try {
            BufferedInputStream in = new BufferedInputStream(
                    new InputStreamReader(System.in));

            System.out.println("Введите размер и значения функции:");
            LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();

            TabulatedFunction LinkedList = FunctionsIO.readTabulatedFunction(in, factory);

            TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
            TabulatedFunction diffFunctionList = differentialOperator.derive(LinkedList);

            System.out.println(diffFunctionList.toString());

        } catch (IOException e){
            e.printStackTrace();
        }
         */

    }
}
