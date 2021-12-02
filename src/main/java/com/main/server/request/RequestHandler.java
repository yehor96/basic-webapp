package com.main.server.request;

import com.main.server.resourceprovider.HomePageProcessor;
import com.main.server.resourceprovider.ResourceReader;
import com.main.server.response.ResponseWriter;
import com.main.exceptions.BadRequestException;
import com.main.exceptions.MethodNotAllowedException;
import com.main.exceptions.ResourceNotFoundException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

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
            byte[] content;
            String uri = request.getUri();
            if (uri.equals("/")) {
                HomePageProcessor homePageProcessor = new HomePageProcessor(webAppPath);
                content = homePageProcessor.getHomePageContent();
            } else {
                ResourceReader resourceReader = new ResourceReader(webAppPath);
                content = resourceReader.read(uri);
            }

            ResponseWriter.writeOk(content, writer);

        } catch (BadRequestException e) {
            ResponseWriter.writeBadRequest(writer);
        } catch (ResourceNotFoundException e) {
            ResponseWriter.writeNotFound(writer);
        } catch (MethodNotAllowedException e) {
            ResponseWriter.writeMethodNotAllowed(writer, e);
        }
    }
}
