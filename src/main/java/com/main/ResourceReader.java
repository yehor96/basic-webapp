package com.main;

import com.main.exceptions.ResourceNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ResourceReader {

    private final String webAppPath;

    public ResourceReader(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public byte[] read(String uri) throws IOException {
        File file = new File(webAppPath, uri);
        byte[] content = new byte[(int) file.length()];

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(content);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e);
        }

        return content;
    }
}
