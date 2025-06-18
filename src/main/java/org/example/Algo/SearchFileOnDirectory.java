package org.example.Algo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFileOnDirectory {
    public static void main(String[] args) {
        ArrayList<File> lisFile = new ArrayList<>();
        searchFiles(new File("D:\\"), lisFile);
        for(File file : lisFile){
            System.out.println(file.getAbsolutePath());
        }
    }

    private static void searchFiles(File rootFile, List<File> listFile) {
        if (rootFile.isDirectory()) {
            System.out.println("searching.... "  + rootFile.getAbsolutePath());
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if(file.isDirectory()){
                        searchFiles(file, listFile);
                    } else {
                        if(file.getName().toLowerCase(Locale.ROOT).endsWith(".jpg")){
                            listFile.add(file);
                        }
                    }
                }
            }
        }
    }
}
