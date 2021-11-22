package com.main;

import com.main.exceptions.ResourceNotFoundException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ResourceReader {

    private final String webAppPath;

    public ResourceReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public String read(String uri) throws IOException {
        File file = new File(webAppPath + uri);
        StringBuilder content = new StringBuilder();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }

        return content.toString();
    }
}
