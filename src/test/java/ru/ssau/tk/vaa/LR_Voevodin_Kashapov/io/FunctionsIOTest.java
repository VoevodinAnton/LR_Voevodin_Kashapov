package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.io;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.LinkedListTabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.ArrayTabulatedFunctionFactory;

import java.io.*;

import static org.testng.Assert.*;

public class FunctionsIOTest {
    double[] xValues = new double[]{1, 2, 3, 4, 5};
    double[] yValues = new double[]{2, 4, 6, 8, 10};

    @Test
    public void testReadWriteTabulatedFunction() throws IOException {
        File fileList = new File("temp/linked list for read.bin");
        TabulatedFunction listTest = new LinkedListTabulatedFunction(xValues, yValues);
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileList))) {
            FunctionsIO.writeTabulatedFunction(out, listTest);
        }


        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileList))) {
            ArrayTabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
            TabulatedFunction arrayTest = FunctionsIO.readTabulatedFunction(in, arrayFactory);

            assertEquals(listTest.getCount(), arrayTest.getCount());

            for (int i = 0; i < listTest.getCount(); i++) {
                assertEquals(listTest.getX(i), arrayTest.getX(i));
                assertEquals(listTest.getY(i), arrayTest.getY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void deleteOnExit() {
        for (File myFile : new File("temp").listFiles())
            if (myFile.isFile() && myFile.delete()) {
                System.out.println(myFile.getName() + " deleted");
            }
    }
}