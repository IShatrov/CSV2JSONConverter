package com.netcracker.edu.ishatrov.csv2jsonconverter;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Converter {
    private static final String SRC_EXTENSION = ".csv";
    private static final String DST_EXTENSION = ".json";

    public static void convert(String path) {
        File file;

        try {
            file = getSrcFile(path);
        } catch (Exception ex) {
            System.out.println("Failed to open file " + path);
            System.out.println(ex.getMessage());
            return;
        }

        String dstName = getDstName(path);

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16));
             PrintWriter writer = new PrintWriter(dstName, StandardCharsets.UTF_16)) {
            new JsonWriter(writer).writeFrom(reader);
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to create destination file");
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IO error");
            System.out.println(ex.getMessage());
        }
    }

    private static int getExtensionIndex(String path) {
        return path.lastIndexOf('.');
    }

    private static String getDstName(String path) {
        return path.substring(0, getExtensionIndex(path)) + DST_EXTENSION;
    }

    private static File getSrcFile(String path) throws FileNotFoundException, IllegalArgumentException {
        File file = new File(path);

        if (!file.isFile()) {
            throw new FileNotFoundException("File " + path + " not found");
        }

        int extensionIndex = getExtensionIndex(path);

        if (!path.substring(extensionIndex).equals(SRC_EXTENSION)) {
            throw new IllegalArgumentException(path + " is not a .csv file");
        }

        return file;
    }
}
