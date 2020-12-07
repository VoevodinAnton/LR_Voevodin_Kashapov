package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import ru.ssau.tk.vaa.LR_Voevodin_Kashapov.exeptions.WrongNumberOfElementsException;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow {

    ErrorWindow(Component parent, Exception e) {}

    public void getErrorWindow(Component parent, Exception e) {
        String message = MessageForException(e);
        JOptionPane.showMessageDialog(parent, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    private String MessageForException(Exception e) {
        if (e instanceof NumberFormatException) {
            return "Неверный формат данных";
        }
        if (e instanceof WrongNumberOfElementsException) {
            return "Вы ввели неверное значение";
        }
        if (e instanceof NullPointerException) {
            return "Что-то пошло не так...";
        }
        return "Неверные данные!";
    }
}