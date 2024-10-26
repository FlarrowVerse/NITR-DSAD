package com.dsad.browser.core;

import java.io.Serializable;
import java.net.URL;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Page implements Serializable {
    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private static final long serialVersionUID = 1L; // for versioning

    // instance members
    private URL url;
    private String title;
    private long visitedCount;
    private boolean isBookmarked;
    private LocalDateTime lastVisited;
    private Set<String> tags;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    
    /**
     * Parameterized constructor
     * @param url
     * @param tags
     */
    public Page(String url, String title, List<String> tags, boolean isBookmarked) {
        try {
            this.url = new URL(url); // initiate the URL
        } catch (MalformedURLException e) {
            System.err.println("The URL you provided is malformed");
            e.printStackTrace();
        }
        this.title = title; // page title
        this.visitedCount = 1; // count initiation
        this.lastVisited = LocalDateTime.now(); // current timestamp
        this.isBookmarked = isBookmarked;
        if (tags != null) {
            this.tags = new HashSet<>(); // add the tags into the set
        }
    }

    /**
     * For easy printing
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.title).append(" - ").append(this.url.toString())
                .append((this.isBookmarked) ? " --> (Bookmarked)\n" : "\n");
        str.append("Visited ").append(this.visitedCount).append(" times, last visited on ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        str.append(this.lastVisited.format(formatter)).append("\n"); // adding last visited time

        if (!tags.isEmpty()) {
            str.append("Tags: ").append(String.join(", ", this.tags)); // adding tags
        }
        
        return str.toString();
    }

    /**
     * Returns true if the current page is bookmarked
     * @return true/false
     */
    public boolean isBookmarked() {
        return this.isBookmarked;
    }

    /**
     * Getter for title
     * @return title of the page
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for URL
     * @return URL of the page
     */
    public URL getURL() {
        return this.url;
    }

    /**
     * Getter for tags
     * @return all tags of the page as a list
     */
    public List<String> getTags() {
        return new ArrayList<>(this.tags);
    }

    /**
     * Getter for the visited Count
     * @return visited count
     */
    public long getVisitedCount() {
        return this.visitedCount;
    }

    /**
     * Getter for the last visited
     * @return last visited
     */
    public String getLastVisited() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.lastVisited.format(formatter);
    }

    /**
     * Sets the bookmark
     * @param flag true/false
     */
    public void setBookmark(boolean flag) {
        this.isBookmarked = flag;
    }

    /**
     * Updates the visitedCount and lastVisited fields
     */
    public void visit() {
        this.visitedCount++;
        this.lastVisited = LocalDateTime.now();
    }
}
