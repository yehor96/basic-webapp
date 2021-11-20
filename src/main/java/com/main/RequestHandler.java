package com.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class RequestHandler {

    private String webAppPath;
    private BufferedReader reader;
    private BufferedWriter writer;

    public RequestHandler(BufferedWriter writer, BufferedReader reader, String webAppPath) {
        this.writer = writer;
        this.reader = reader;
        this.webAppPath = webAppPath;
    }

    public void handle() {
//        Request request = RequestParser.parse(reader);
//        String content = ResourceReader.read(request.getUri());
//        ResponseWriter.write(content, writer);
    }
}
