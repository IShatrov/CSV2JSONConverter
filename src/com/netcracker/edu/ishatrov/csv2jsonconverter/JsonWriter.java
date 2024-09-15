package com.netcracker.edu.ishatrov.csv2jsonconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

/**
 * A class that converts CSV to JSON and writes to JSON.
 */
public class JsonWriter {
    private static final String START = "{";
    private static final String END = "}";
    private static final String SEPARATOR_CSV = ",";
    private static final String SEPARATOR_JSON = ":";
    private static final String COMMENT_INDICATOR_CSV = "#";
    private static final String COMMENT_INDICATOR_JSON = "//";
    private static final String LINE_SEPARATOR_JSON = ",";

    private final Writer writer;

    private int currentLine;

    public JsonWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Reads data from reader, converts this data to JSON and writes the data to writer.
     * @param reader CSV file to read from.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If the CSV file is not valid.
     */
    public void writeFrom(BufferedReader reader) throws IOException, IllegalArgumentException {
        currentLine = -1;

        writer.write(START + "\n");

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            currentLine++;

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }


            writeLine(line);
        }

        writer.write(END);
    }

    /**
     * Converts a line to JSON format and writes it to the file.
     * @param line Line to write.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If the CSV file is not valid.
     */
    private void writeLine(String line) throws IOException, IllegalArgumentException {
        if (line.contains(COMMENT_INDICATOR_CSV)) {
            writeComment(line);
        }

        if (line.contains(SEPARATOR_CSV)) {
            writeData(line);
        }
    }

    /**
     * Writes a JSON comment.
     * @param line Line to convert to JSON comment.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If the CSV file is not valid.
     */
    private void writeComment(String line) throws IOException, IllegalArgumentException {
        int commentStartIndex = line.indexOf(COMMENT_INDICATOR_CSV);

        if (!line.substring(0, commentStartIndex).trim().isEmpty()) {
            throw new IllegalArgumentException("Unresolved characters before comment on line " + currentLine);
        }

        writer.write(COMMENT_INDICATOR_JSON + line.substring(commentStartIndex + 1) + "\n");
    }

    /**
     * Writes JSON data.
     * @param line Line to convert to JSON data.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If the CSV file is not valid.
     */
    private void writeData(String line) throws IOException, IllegalArgumentException {
        int separatorIndex = line.indexOf(SEPARATOR_CSV);

        if (line.substring(separatorIndex + 1).contains(SEPARATOR_CSV)) {
            throw new IllegalArgumentException("Stray " + SEPARATOR_CSV + " on line " + currentLine);
        }

        writer.write("""
                \t"%s" %s "%s" %s
                """.formatted(line.substring(0, separatorIndex), SEPARATOR_JSON,
                line.substring(separatorIndex + 1), LINE_SEPARATOR_JSON));
    }
}
