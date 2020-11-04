package io;

import java.io.File;

public class DeleteFile {
    public static void main(String[] args) {
        for (File myFile : new File("temp").listFiles())
            if (myFile.isFile() && myFile.delete()) {
                System.out.println(myFile.getName() + " deleted");
            }
    }
}
