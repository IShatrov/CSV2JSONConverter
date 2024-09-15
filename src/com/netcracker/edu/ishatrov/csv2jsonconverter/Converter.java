package com.netcracker.edu.ishatrov.csv2jsonconverter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * A class that is used to open a UTF-16 encoded CSV file and convert it to a JSON file using JsonWriter class.
 */
public class Converter {
    private static final String SRC_EXTENSION = ".csv";
    private static final String DST_EXTENSION = ".json";

    /**
     * Converts CSV file to JSON.
     * @param path Path to the source CSV file.
     */
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

    /**
     * Gets index of the last '.' character in the path.
     * @param path Path to the source file.
     * @return Index of the last '.' character.
     */
    private static int getExtensionIndex(String path) {
        return path.lastIndexOf('.');
    }

    /**
     * Constructs destination file name.
     * @param path Path to the source file.
     * @return The same string as path except with .json extension.
     */
    private static String getDstName(String path) {
        return path.substring(0, getExtensionIndex(path)) + DST_EXTENSION;
    }

    /**
     * Gets source file.
     * @param path Path to the source file.
     * @return File() representation of the file at path.
     * @throws FileNotFoundException If file was not found.
     * @throws IllegalArgumentException If file is not a CSV file.
     */
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
