package com.dsad.browser.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BrowserHistory implements Serializable {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private static final long serialVersionUID = 1L; // for versioning

    // instance members
    private DoublyLinkedList<Page> history;
    private List<Page> bookmarks;
    private LocalDate date;
    

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Default constructor - init
     */
    public BrowserHistory() {
        this.history = new DoublyLinkedList<>();
        this.date = LocalDate.now(); // get today's date
    }

    /**
     * Getter for the date of the current history
     * @return formatted date
     */
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.date.format(formatter);
    }

    /**
     * Get the current page in the history
     * @return current page as string
     */
    public String getCurrentPage() {
        return this.history.getCurrentNode().toString();
    }

    /**
     * Moves back the current pointer in the linked list
     */
    public void goBack() {
        this.history.moveBack();
        this.navs.add(Navigation.BACK);
    }

    /**
     * Moves forward the current pointer in the linked list
     */
    public void goForward() {
        this.history.moveForward();
        this.navs.add(Navigation.FORWARD);
    }

    public void visitNewPage(String url, String title, List<String> tags, boolean bookmark) {
        Page newPage = new Page(url, title, tags);
        this.history.append(newPage); // add the new page to the history
        if (bookmark) this.bookmarks.add(newPage); // add the page to bookmarks as well
        this.navs.add(Navigation.NEWPAGE);
    }

    /**
     * Handles filename fetch
     * @param filename
     * @return full path of the browser history file
     */
    public static String getFilePath(String filename) {
        String fullPath = PathConfig.getFullPath(filename);
        try {
            PathConfig.ensureDirectoryExists();
            return fullPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Save the given history
     * @param browserHistory object to be saved
     */
    public static void saveHistory(BrowserHistory browserHistory) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filename = "history_" + browserHistory.date.format(formatter);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(filename)))) {
            oos.writeObject(browserHistory);
            System.out.println("History for " + browserHistory.date.format(formatter) + " successfully saved to file.");
        } catch (IOException e) {
            System.err.println("ERROR: Could not save " + filename);
            e.printStackTrace();
        }
    }

    /**
     * Load a history file given its name
     * @param date date whose history is to be loaded
     * @return BrowserHistory object
     */
    public static BrowserHistory loadHistory(String date) {
        BrowserHistory history = null;
        String filename = "history_" + date;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilePath(filename)))) {
            history = (BrowserHistory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR: Could not load " + filename);
            e.printStackTrace();
        }        

        return history;
    }

    /**
     * Delete the history of a given date
     * @param date history to be deleted
     */
    public static boolean deleteHistory(String date) {
        File file = new File(getFilePath("history_" + date));

        if (file.exists()) {
            return file.delete();
        }
        System.err.println("File not found!");
        return false;
    }

    
}
