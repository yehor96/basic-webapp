package com.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileAnalyzer {

    public static List<String> getFilesInDir(String rootDir) {
        File rootFile = new File(rootDir);
        if (!rootFile.isDirectory()) {
            return Collections.emptyList();
        }
        List<String> files = getFilesInDir(rootFile);
        return getFormattedPaths(files, rootFile);
    }

    private static List<String> getFilesInDir(File dir) {
        File[] files = dir.listFiles();
        if (Objects.isNull(files)) {
            return Collections.emptyList();
        }

        List<String> paths = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                paths.add(file.getPath());
            } else {
                paths.addAll(getFilesInDir(file));
            }
        }
        return paths;
    }

    private static List<String> getFormattedPaths(List<String> initialFiles, File root) {
        List<String> files = new ArrayList<>();
        for (String initialFile : initialFiles) {
            String formattedFile = initialFile
                    .replace(root.toString(), "")
                    .replace("\\", "/");
            files.add(formattedFile);
        }
        return files;
    }

}
