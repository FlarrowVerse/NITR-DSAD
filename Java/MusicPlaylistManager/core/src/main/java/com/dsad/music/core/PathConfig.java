package com.dsad.music.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PathConfig {
    private static String basePath;

    static {
        try {
            // Load properties file from resources
            Properties properties = new Properties();
            properties.load(PathConfig.class.getClassLoader().getResourceAsStream("config.properties"));
            
            // Get the configured base path
            basePath = properties.getProperty("output.basePath", "../default-output-dir");
        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to relative path if configuration fails
            basePath = "../default-output-dir";
        }
    }

    public static String getFullPath(String fileName) {
        // Combine base path with the filename to form full path
        return Paths.get(basePath, fileName).toString();
    }
    
    public static void ensureDirectoryExists() throws IOException {
        // Ensure that the directory exists (creates if it doesn't)
        Files.createDirectories(Paths.get(basePath));
    }
}
