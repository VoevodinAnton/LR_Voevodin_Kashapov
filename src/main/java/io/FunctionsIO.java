package io;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.Point;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.TabulatedFunction;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions.factory.TabulatedFunctionFactory;

import java.io.*;

final class FunctionsIO {
    FunctionsIO() {
        throw new UnsupportedOperationException("Unavailable operation");
    }

    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream out = new DataOutputStream(outputStream);
        out.writeInt(function.getCount());
        for (Point point: function){
            out.writeDouble(point.x);
            out.writeDouble(point.y);
        }
        out.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream in = new DataInputStream(inputStream);
        int count = in.readInt();

        double[] xValues = new double[count];
        double[] yValues = new double[count];

        for(int i = 0; i < count; i++){
            xValues[i] = in.readDouble();
            yValues[i] = in.readDouble();
        }
        return factory.create(xValues, yValues);
    }
}
