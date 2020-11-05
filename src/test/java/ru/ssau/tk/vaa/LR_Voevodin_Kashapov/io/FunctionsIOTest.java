package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io;

import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;

import java.io.*;

import static org.testng.Assert.*;

public class FunctionsIOTest {
    double[] xValues = new double[]{1, 2, 3, 4, 5};
    double[] yValues = new double[]{2, 4, 6, 8, 10};

    LinkedListTabulatedFunction list = new LinkedListTabulatedFunction(xValues, yValues);

    @Test
    public void testWriteTabulatedFunction() {
    }

    @Test
    public void testTestWriteTabulatedFunction() {
    }

    @Test
    public void testReadTabulatedFunction(){
        File fileList = new File("temp/linked list for read.bin");
        TabulatedFunction listTest = new LinkedListTabulatedFunction(xValues, yValues);
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileList))){
            FunctionsIO.writeTabulatedFunction(out, listTest);
        } catch (IOException e) {
            e.printStackTrace();
        }


       try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileList))) {
           ArrayTabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
           TabulatedFunction arrayTest = FunctionsIO.readTabulatedFunction(in, arrayFactory);

           assertEquals(listTest.getCount(), arrayTest.getCount());

           for(int i = 0; i < listTest.getCount(); i++){
               assertEquals(listTest.getX(i), arrayTest.getX(i));
               assertEquals(listTest.getY(i), arrayTest.getY(i));
           }
       } catch (IOException e){
           e.printStackTrace();
       }
    }

    @Test
    public void testSerialize() {
    }

    @Test
    public void testDeserialize() {
    }
}