package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.ArrayIsNotSortedException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.FunctionAreNotSimilarException;
import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ErrorWindow extends JDialog {

    ErrorWindow(Component parent, Exception e) {
        getErrorWindow(parent, e);
        setModal(true);
    }

    public void getErrorWindow(Component parent, Exception e) {
        String message = MessageForException(e);
        JOptionPane.showMessageDialog(parent, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    private String MessageForException(Exception e) {
        if (e instanceof NumberFormatException) {
            return "Неверный формат данных!";
        }
        if (e instanceof WrongNumberOfElementsException) {
            return "Вы ввели неверное значение!";
        }
        if (e instanceof ArrayIsNotSortedException) {
            return "Элементы X должны быть упорядочены!";
        }
        if (e instanceof NullPointerException) {
            return "Что-то пошло не так...";
        }
        if (e instanceof IOException)
            return "Не удалось прочитать/записать файл";
        if (e instanceof FunctionAreNotSimilarException)
            return "Абсциссы функций не совпадают";
        return "Неверные данные!";
    }
}