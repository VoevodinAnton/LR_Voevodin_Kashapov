package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UnmodifiableAndStrictTest {
    private final double[] x = new double[]{1, 2, 3, 4, 5};
    private final double[] y = new double[]{2, 4, 6, 8, 10};

    private ArrayTabulatedFunction array() {
        return new ArrayTabulatedFunction(x, y);
    }

    private LinkedListTabulatedFunction list() {
        return new LinkedListTabulatedFunction(x, y);
    }

    private final UnmodifiableTabulatedFunction unModArray = new UnmodifiableTabulatedFunction(array());
    private final StrictTabulatedFunction strictArray = new StrictTabulatedFunction(array());

    private final UnmodifiableTabulatedFunction unModList = new UnmodifiableTabulatedFunction(list());
    private final StrictTabulatedFunction strictList = new StrictTabulatedFunction(list());

    private StrictTabulatedFunction strictUnModList() {
        return new StrictTabulatedFunction(unModList);
    }

    private StrictTabulatedFunction strictUnModArray() {
        return new StrictTabulatedFunction(unModArray);
    }

    private UnmodifiableTabulatedFunction unModStrictArray() {
        return new UnmodifiableTabulatedFunction(strictArray);
    }

    private UnmodifiableTabulatedFunction unModStrictList() {
        return new UnmodifiableTabulatedFunction(strictList);
    }

    @Test
    public void testSetY() {
        StrictTabulatedFunction strUnmLis = strictUnModList();
        StrictTabulatedFunction strUnmArr = strictUnModArray();
        UnmodifiableTabulatedFunction unmStrLis = unModStrictList();
        UnmodifiableTabulatedFunction unmStrArr = unModStrictArray();

        assertThrows(UnsupportedOperationException.class, () -> strUnmLis.setY(0, 10));
        assertThrows(UnsupportedOperationException.class, () -> strUnmArr.setY(1, 15));
        assertThrows(UnsupportedOperationException.class, () -> unmStrLis.setY(2, 20));
        assertThrows(UnsupportedOperationException.class, () -> unmStrArr.setY(3, 25));
    }

    @Test
    public void testApply() {
        StrictTabulatedFunction strUnmLis = strictUnModList();
        StrictTabulatedFunction strUnmArr = strictUnModArray();
        UnmodifiableTabulatedFunction unmStrLis = unModStrictList();
        UnmodifiableTabulatedFunction unmStrArr = unModStrictArray();

        assertThrows(UnsupportedOperationException.class, () -> strUnmLis.apply(0));
        assertThrows(UnsupportedOperationException.class, () -> unmStrLis.apply(0.5));
        assertThrows(UnsupportedOperationException.class, () -> strUnmArr.apply(0.75));
        assertThrows(UnsupportedOperationException.class, () -> unmStrArr.apply(0.25));
    }
}