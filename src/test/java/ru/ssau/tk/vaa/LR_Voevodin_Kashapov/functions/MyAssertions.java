package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import org.testng.Assert;

public class MyAssertions {
    public static Assert.ThrowingRunnable assertDoesNotThrow(FailingRunnable action){
        try{
            action.run();
        }
        catch(Exception ex){
            throw new Error("expected action not to throw, but it did!", ex);
        }
        return null;
    }
}

@FunctionalInterface interface FailingRunnable { void run() throws Exception; }
