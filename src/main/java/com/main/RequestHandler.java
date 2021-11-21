package com.main;

import com.main.exceptions.BadRequestException;
import com.main.exceptions.ResourceNotFoundException;
import com.main.helper.FileAnalyzer;
import com.main.request.Request;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class RequestHandler {

    private final String webAppPath;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public RequestHandler(BufferedWriter writer, BufferedReader reader, String webAppPath) {
        this.writer = writer;
        this.reader = reader;
        this.webAppPath = webAppPath;
    }

    public void handle() throws IOException {
        try {
            Request request = RequestParser.parse(reader);
            String content;
            String uri = request.getUri();
            if (uri.equals("/")) {
                content = getHomePageContent();
            } else {
                ResourceReader resourceReader = new ResourceReader(webAppPath);
                content = resourceReader.read(uri);
            }

            ResponseWriter.writeOk(content, writer);
        } catch (BadRequestException e) {
            ResponseWriter.writeBadRequest(writer);
        } catch (ResourceNotFoundException e) {
            ResponseWriter.writeNotFound(writer);
        }
    }

    private String getHomePageContent() {
        StringBuilder content = new StringBuilder("This is Home Page. Feel free to browse these files: \n\n");

        List<String> availablePaths = FileAnalyzer.getFilesInDir(webAppPath);
        for (String path : availablePaths) {
            content.append(path).append("\n");
        }

        return content.toString();
    }
}
