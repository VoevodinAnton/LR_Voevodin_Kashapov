package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.ui;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {

    public static File getFinalNewDestinationFile(File destinationFolder, File fileToCopy){

        String destFolderPath = destinationFolder.getAbsolutePath()+File.separator;
        File newFile = new File(destFolderPath + fileToCopy.getName());
        String filename=fileToCopy.getName();
        String nameWithoutExtentionOrIncrement;
        String extension = getFileExtension(filename);

        if(extension!=null){
            extension="."+extension;
            int extInd = filename.lastIndexOf(extension);
            nameWithoutExtentionOrIncrement = new StringBuilder(filename).replace(extInd, extInd+extension.length(),"").toString();
        }
        else{
            extension="";
            nameWithoutExtentionOrIncrement = filename;
        }

        int c=0;
        int indexOfClose = nameWithoutExtentionOrIncrement.lastIndexOf(")");
        int indexOfOpen = nameWithoutExtentionOrIncrement.lastIndexOf("(");

        if(indexOfClose!=-1 && indexOfClose!=-1 && indexOfClose==nameWithoutExtentionOrIncrement.length()-1 && indexOfClose > indexOfOpen && indexOfOpen!=0){
            String possibleNumber = nameWithoutExtentionOrIncrement.substring(indexOfOpen+1, indexOfClose);
            try{
                c = Integer.parseInt(possibleNumber);
                nameWithoutExtentionOrIncrement=nameWithoutExtentionOrIncrement.substring(0, indexOfOpen);
            }catch(Exception e){c=0;}
        }

        while(newFile.exists()){
            c++;
            String path = destFolderPath + nameWithoutExtentionOrIncrement +"(" + c + ")" + extension;
            newFile = new File(path);
        }
        return newFile;
    }


    public static String getFileExtension(String filename) {
        if (filename == null) {  return null; }
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int indexOfLastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int extensionPos = filename.lastIndexOf('.');
        int lastSeparator = indexOfLastSeparator;
        int indexOfExtension = lastSeparator > extensionPos ? -1 : extensionPos;
        int index = indexOfExtension;
        if (index == -1) {
            return null;
        } else {
            return filename.substring(index + 1).toLowerCase();
        }
    }
}
