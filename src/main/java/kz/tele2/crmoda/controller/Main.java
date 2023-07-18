package kz.tele2.crmoda.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final File folder = new File("/Users/alitolebek/Desktop/files");
        listFilesForFolder(folder);
    }

    public static void listFilesForFolder(final File folder) throws FileNotFoundException {
        for (final File fileEntry : folder.listFiles()) {
            Scanner sc = new Scanner(fileEntry);
        }
    }
}
