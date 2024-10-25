package com.dsad.browser.core;

import java.io.Serializable;
import java.net.URL;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;


import java.util.HashSet;
import java.util.List;

public class Page implements Serializable {
    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private static final long serialVersionUID = 1L; // for versioning

    // instance members
    private URL url;
    private String title;
    private long visitedCount;
    private LocalDateTime lastVisited;
    private Set<String> tags;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    
    /**
     * Parameterized constructor
     * @param url
     * @param tags
     */
    public Page(String url, String title, List<String> tags) {
        try {
            this.url = new URL(url); // initiate the URL
        } catch (MalformedURLException e) {
            System.err.println("The URL you provided is malformed");
            e.printStackTrace();
        }
        this.title = title; // page title
        this.visitedCount = 1; // count initiation
        this.lastVisited = LocalDateTime.now(); // current timestamp
        if (tags == null) {
            this.tags = new HashSet<>(); // add the tags into the set
        }
    }

    /**
     * For easy printing
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.title).append(" - ").append(this.url.toString()).append("\n");
        str.append("Visited ").append(this.visitedCount).append(" times, last visited on ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        str.append(this.lastVisited.format(formatter)).append("\n"); // adding last visited time
        str.append("Tags: ").append(String.join(", ", this.tags)); // adding tags
        
        return str.toString();
    }
}
