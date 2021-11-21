package com.main.server;

import com.main.helper.FileAnalyzer;
import com.main.RequestHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Server {

    private int port;
    private String webAppPath;

    public void setPort(int port) {
        this.port = port;
    }

    public void setWebAppPath(String path) {
        webAppPath = path;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
                ) {
                    RequestHandler requestHandler = new RequestHandler(writer, reader, webAppPath);
                    requestHandler.handle();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void processRequest(ServerSocket serverSocket) throws IOException {
        try (Socket socket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            List<String> content = readRequest(reader);
            if (!content.isEmpty()) {
                String resourceLine = content.get(0);
                String resource = resourceLine.substring(resourceLine.indexOf("/"), resourceLine.indexOf(" HTTP"));
                sendResource(resource, writer);
            }
        }
    }

    private void sendResource(String resource, BufferedWriter writer) throws IOException {
        File file = new File(webAppPath + resource);
        if (!file.exists()) {
            writer.write("Not able to find resource [" + resource + "]. Please use one of the following: \n");

            List<String> availablePaths = FileAnalyzer.getFilesInDir(webAppPath);
            for (String path : availablePaths) {
                writer.write(path);
                writer.newLine();
            }
        } else {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                String line;
                writer.write("HTTP/1.1 200 OK\n\n");
                while ((line = fileReader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    private List<String> readRequest(BufferedReader reader) throws IOException {
        List<String> requestContent = new ArrayList<>();
        while (true) {
            String message = reader.readLine();
            System.out.println(message);
            if (Objects.isNull(message) || message.isBlank()) {
                break;
            }
            requestContent.add(message);
        }
        return requestContent;
    }

}