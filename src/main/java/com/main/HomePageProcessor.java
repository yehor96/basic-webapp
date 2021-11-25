package com.main;

import com.main.helper.FileAnalyzer;

import java.util.List;

public class HomePageProcessor {

    private final String webAppPath;

    public HomePageProcessor(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public byte[] getHomePageContent() {
        StringBuilder content = new StringBuilder("This is Home Page. Feel free to browse these files: \n\n");

        List<String> availablePaths = FileAnalyzer.getFilesInDir(webAppPath);
        for (String path : availablePaths) {
            content.append(path).append("\n");
        }

        return content.toString().getBytes();
    }
}
