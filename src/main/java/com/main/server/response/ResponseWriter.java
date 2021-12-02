package com.main.server.response;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter {

    private static final String NOT_FOUND_ERROR_MESSAGE = "Not able to find the file with specified path";

    public static void writeOk(byte[] content, BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 200 OK");
        writer.write(System.lineSeparator());
        writer.write(System.lineSeparator());
        for (byte b : content) {
            writer.write(b);
        }
    }

    public static void writeNotFound(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 404 Not Found");
        writer.write(System.lineSeparator());
        writer.write(System.lineSeparator());
        writer.write(NOT_FOUND_ERROR_MESSAGE);
    }

    public static void writeBadRequest(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.1 400 Bad Request");
        writer.write(System.lineSeparator());
        writer.write(System.lineSeparator());
    }

    public static void writeMethodNotAllowed(BufferedWriter writer, Exception e) throws IOException {
        writer.write("HTTP/1.1 405 Method Not Allowed");
        writer.write(System.lineSeparator());
        writer.write(System.lineSeparator());
        writer.write(e.getMessage());
    }
}
