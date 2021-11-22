package com.main;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {

    private static final String NOT_FOUND_ERROR_MESSAGE = "Not able to find the file with specified path";

    public static void writeOk(String content, BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 200 OK\n\n");
        writeContent(content, writer);
    }

    public static void writeNotFound(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 404 Not Found\n\n");
        writeContent(NOT_FOUND_ERROR_MESSAGE, writer);
    }

    public static void writeBadRequest(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 400 Bad Request\n\n");
    }

    public static void writeMethodNotAllowed(BufferedWriter writer, Exception e) throws IOException {
        writer.write("HTTP/1.1 405 Method Not Allowed\n\n");
        writeContent(e.getMessage(), writer);
    }

    private static void writeContent(String content, BufferedWriter writer) throws IOException {
        for (String line : content.split("\n")) {
            writer.write(line);
            writer.newLine();
        }
    }
}
