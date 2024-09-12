package com.netcracker.edu.ishatrov.csv2jsonconverter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Converter {
    private static final String srcExtension = ".csv";
    private static final String dstExtension = ".json";

    public static void convert(String path) {
        File file = new File(path);

        if (!file.isFile()) {
            System.out.println("File " + path + " not found");
            return;
        }

        int extensionIndex = path.lastIndexOf('.');

        if (!path.substring(extensionIndex).equals(srcExtension)) {
            System.out.println(path + " is not a .csv file");
            return;
        }

        String dstName = path.substring(0, extensionIndex) + dstExtension;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16));
             PrintWriter writer = new PrintWriter(dstName, StandardCharsets.UTF_16)) {
            String line;

            do {
                line = reader.readLine();
                System.out.println(line);
            } while (line != null);
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to create destination file");
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IO error");
            System.out.println(ex.getMessage());
        }
    }
}
