package com.dsad.browser.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BrowserHistory {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    // instance members
    private DoublyLinkedList<Page> history;
    private LocalDate date;

    // static properties
    private static List<Page> bookmarks;
    private static LocalDate loadedHistoryDate;
    private static DoublyLinkedList<Page> loadedHistory;
    private static Stack<Navigation> undo, redo;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    // init of undo and redo
    static {
        bookmarks = new ArrayList<>();
        loadedHistoryDate = null;
        undo = new Stack<>();
        redo = new Stack<>();
    }

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
     * For easy printing of the list
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        str.append("History for ").append(this.date.format(formatter)).append("\n\n");

        for (long i = this.history.getSize(); i >= 1; i--) {
            Page curr = this.history.getNodeData(i);
            str.append(i + "> ").append(curr).append("\n\n");
        }
        return str.toString();
    }

    /**
     * Get the current page in the history
     * @return current page as string
     */
    public String getCurrentPage() {
        Page currentPage = this.history.getCurrentNode();
        return (currentPage == null)? "": currentPage.toString();
    }

    /**
     * Moves back the current pointer in the linked list
     * Also updates the visit count and timestamp
     */
    public void goBack() {
        this.history.moveBack();
        this.history.getCurrentNode().visit();
        undo.push(Navigation.BACK); // add the action to be undone
    }

    /**
     * Moves forward the current pointer in the linked list
     * Also udpates the visit count and timestamp
     */
    public void goForward() {
        this.history.moveForward();
        this.history.getCurrentNode().visit();
        undo.push(Navigation.FORWARD); // add the action to be undone
    }

    /**
     * Visit a new page and add it to the history
     * @param url URL of the new page
     * @param title title of the new page
     * @param tags tags attached to the new page
     * @param bookmark if this is to be bookmarked or not
     */
    public void visitNewPage(String url, String title, List<String> tags, boolean bookmark) {
        Page newPage = new Page(url, title, tags, bookmark);
        if (newPage.getURL() == null) {
            System.out.println("Cannot visit malformed URL!");
        } else {
            this.history.append(newPage); // add the new page to the history
            if (bookmark) bookmarks.add(newPage); // add the page to bookmarks as well
            undo.push(Navigation.NEWPAGE); // add the action to be undone
        }
    }

    /**
     * Undoes the last navigation action
     */
    public void undo() {
        switch (undo.peek()) { // fetch the last value of the undo list
            case NEWPAGE: this.history.deleteNode(this.history.getSize(), null); // delete the last node
                break;
            case BACK: this.goForward();
                break;
            case FORWARD: this.goBack();
                break;
            case BOOKMARK: Page lastBookmark = bookmarks.get(bookmarks.size()-1);
                this.history.getNodeData(this.history.indexOf(page -> 
                    page.getTitle().equalsIgnoreCase(lastBookmark.getTitle()) && 
                    page.getURL().equals(lastBookmark.getURL()))).setBookmark(false);

                bookmarks.remove(bookmarks.size()-1); // remove the last bookmark
                break;
            case NONE: return;
        }

        redo.push(Navigation.getReverse(undo.pop())); // storing the reverse of the action
    }

    /**
     * Redoes the last navigation action
     */
    public void redo() {
        switch (redo.peek()) { // fetch the last value of the redo list
            case BACK: this.goBack();
                break;
            case FORWARD: this.goForward();
                break;
            default: return;
        }

        redo.push(undo.pop()); // storing the action itself
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
            oos.writeObject(browserHistory.history);
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
    @SuppressWarnings("unchecked")
    public static void loadHistory(String date) {
        String filename = "history_" + date;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilePath(filename)))) {
            loadedHistory = (DoublyLinkedList<Page>) ois.readObject();
            loadedHistoryDate = LocalDate.parse(date);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR: Could not load " + filename);
            e.printStackTrace();
        }
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

    /**
     * Searches for a page in the history and shows its position
     * @param title
     * @param url
     */
    public void searchHistory(String title, String url) {
        long index = this.history
                .indexOf(page -> page.getTitle().equalsIgnoreCase(title) || page.getURL().toString().equals(url));

        System.out.printf("%s %s %s.\n",
                ((index == -1) ? "Not found" : "Found"),
                (title.length() == 0) ? url : title,
                ((index == -1) ? "" : " at " + index));
    }

    /**
     * Given either the title or the URL or the index loads a page
     * @param title
     * @param index
     */
    public void loadPage(String title, String url, long index) {
        boolean loaded = false;
        if (index != -1) {
            this.history.moveNode(index, this.history.getSize(), null);
        } else if (url.length() == 0) {
            this.history.moveNode(-1, this.history.getSize(), page -> page.getTitle().equalsIgnoreCase(title));
        } else {
            this.history.moveNode(-1, this.history.getSize(), page -> page.getURL().toString().equals(url));
        }
        this.history.setCurrent(null, this.history.getSize()); // setting the new page as the current page
        this.history.getCurrentNode().visit(); // updating count and timestamp

        System.out.printf("%s the requested page...\n", (loaded)? "Could not load ": "Loaded ");
    }

    /**
     * Loads a bookmarked page
     * @param title
     * @param url
     */
    public void loadBookmarkedPage(String title, String url) {
        boolean loaded = false;
        Page query = null;

        // load the bookmark quickly from the list
        for (Page page : bookmarks) {
            if (page.getTitle().equalsIgnoreCase(title) || page.getURL().toString().equals(url)) {
                query = page;
                loaded = true;
                break;
            }
        }
        if (!loaded && query != null) { // if bookmark present
            this.history.append(query);
            this.history.setCurrent(null, this.history.getSize()); // setting the new page as the current page   
        }
        System.out.printf("%s the requested page...\n", ((loaded)? "Could not load ": "Loaded "));
    }

    public void removePage(String title, String url, long index) {
        if (index != -1) { // if index was provided
            this.history.deleteNode(index, null);
        } else {
            this.history.deleteNode(-1, page -> page.getTitle().equalsIgnoreCase(title) || page.getURL().toString().equals(url));
        }
    }

    /**
     * Clears the history
     */
    public void clearHistory() {
        this.history.clear(); // clear the history first
        undo.clear(); // clearing the undo actions
        redo.clear(); // clearing the redo actions
        loadedHistory = null; // removing the loaded history
        loadedHistoryDate = null; // removing the loaded history
    }

    /**
     * adding the current page to bookmarks list
     */
    public void bookmarkCurrentPage() {
        bookmarks.add(this.history.getCurrentNode()); // adding the current page to bookmarks
        this.history.getCurrentNode().setBookmark(true);
    }

    /**
     * Checks if a file was already loaded
     * @return true/false
     */
    public static boolean isLoaded() {
        return (loadedHistory != null);
    }

    public static String getLoadedHistory() {
        StringBuilder str = new StringBuilder();
        str.append(loadedHistoryDate.toString()).append("\n");
        str.append(loadedHistory).append("\n");
        return str.toString();
    }

    /**
     * Returns the page data after searching it in the loaded history file
     * @param title
     * @param url
     * @param position
     * @return
     */
    public static Page searchLoadedHistory(String title, String url, long position) {
        if (position != -1) {
            return loadedHistory.getNodeData(position);
        } else {
            position = loadedHistory.indexOf(page -> page.getTitle().equalsIgnoreCase(title) || page.getURL().toString().equals(url));
            System.out.printf("%s %s %s.\n",
                ((position == -1) ? "Not found" : "Found"),
                (title.length() == 0) ? url : title,
                ((position == -1) ? "" : " at " + position));
            return loadedHistory.getNodeData(position);
        }
    }

    /**
     * Removes a page from an old history file
     * @param title
     * @param url
     * @param position
     */
    public static void removeLoadedHistoryPage(String title, String url, long position) {
        loadedHistory.deleteNode(position,
                page -> page.getTitle().equalsIgnoreCase(title) || page.getURL().toString().equals(url));
    }

    /**
     * Save the loaded history
     * @param browserHistory object to be saved
     */
    public static void saveLoadedHistory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filename = "history_" + loadedHistoryDate.format(formatter);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(filename)))) {
            oos.writeObject(loadedHistory);
            System.out.println("History for " + loadedHistoryDate.format(formatter) + " successfully saved to file.");
        } catch (IOException e) {
            System.err.println("ERROR: Could not save " + filename);
            e.printStackTrace();
        }
    }

    /**
     * Shows all the current bookmarks
     */
    public void showBookmarks() {
        if (bookmarks.isEmpty()) {
            System.out.println("No bookmarks added yet!");
        } else {
            for (Page bookmarkPage : bookmarks) {
                System.out.println(bookmarkPage);
            }
        }
    }
}
