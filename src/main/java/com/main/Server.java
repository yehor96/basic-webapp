package com.main;

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
                process(serverSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(ServerSocket serverSocket) throws IOException {
        try (Socket socket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            List<String> content = readClient(reader);
            List<String> resources = getRequestedResources(content.get(0));

            File resource = new File(getResource(resources.get(0)));
            try (BufferedReader br = new BufferedReader(new FileReader(resource))) {
                String line;
                writer.write("HTTP/1.1 200 OK\n\n");
                while ((line = br.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }

    private String getResource(String resource) {
        return webAppPath.concat("/").concat(resource);
    }

    private List<String> getRequestedResources(String line) {
        String resources = line.substring(line.indexOf("/") + 1, line.indexOf(" HTTP"));
        return List.of(resources.split(" "));
    }

    private List<String> readClient(BufferedReader reader) throws IOException {
        List<String> clientContent = new ArrayList<>();
        while (true) {
            String message = reader.readLine();
            System.out.println(message); //TODO remove
            if (message.isBlank()) {
                break;
            }
            clientContent.add(message);
        }
        return clientContent;
    }

}