package com.example.attendance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import jakarta.annotation.PostConstruct;
import java.io.File;

@Configuration
@EnableConfigurationProperties(FingerprintConfiguration.FingerprintProperties.class)
public class FingerprintConfiguration {

    @PostConstruct
    public void checkLibraryPath() {
        String libraryPath = System.getProperty("java.library.path");
        System.out.println("Current java.library.path: " + libraryPath);
        
        // Check if SecuGen libraries are accessible
        String[] commonPaths = {
            "C:\\Windows\\System32",
            "C:\\Program Files\\SecuGen\\FDx SDK Pro\\bin",
            "C:\\Program Files (x86)\\SecuGen\\FDx SDK Pro\\bin",
            "./lib",
            "./native"
        };
        
        System.out.println("Checking for SecuGen libraries in common locations:");
        for (String path : commonPaths) {
            File dir = new File(path);
            if (dir.exists()) {
                File[] files = dir.listFiles((dir1, name) -> 
                    name.toLowerCase().contains("secugen") || 
                    name.toLowerCase().contains("sgfp") ||
                    name.toLowerCase().contains("fdx"));
                
                if (files != null && files.length > 0) {
                    System.out.println("Found SecuGen files in: " + path);
                    for (File file : files) {
                        System.out.println("  - " + file.getName());
                    }
                }
            }
        }
        
        // Check if the required DLL is in PATH
        try {
            System.loadLibrary("sgfplib");
            System.out.println("Successfully loaded sgfplib");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load sgfplib: " + e.getMessage());
            System.err.println("Make sure the following files are in your PATH or java.library.path:");
            System.err.println("  - sgfplib.dll");
            System.err.println("  - SgFpLib.dll");
            System.err.println("  - Any other SecuGen SDK dependencies");
        }
    }

    @ConfigurationProperties(prefix = "fingerprint")
    public static class FingerprintProperties {
        private int captureTimeout = 10000; // 10 seconds
        private int maxRetries = 3;
        private boolean autoReconnect = true;
        private String deviceType = "AUTO";
        
        // Getters and setters
        public int getCaptureTimeout() { return captureTimeout; }
        public void setCaptureTimeout(int captureTimeout) { this.captureTimeout = captureTimeout; }
        
        public int getMaxRetries() { return maxRetries; }
        public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }
        
        public boolean isAutoReconnect() { return autoReconnect; }
        public void setAutoReconnect(boolean autoReconnect) { this.autoReconnect = autoReconnect; }
        
        public String getDeviceType() { return deviceType; }
        public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    }
}