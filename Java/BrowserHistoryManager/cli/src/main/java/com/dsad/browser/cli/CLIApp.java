package com.dsad.browser.cli;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.dsad.browser.core.Navigation;
import com.dsad.browser.core.BrowserHistory;

public class CLIApp {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private Scanner sc; // for input
    private BrowserHistory manager;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Parameterized constructor
     * @param sc
     */
    public CLIApp(Scanner sc) {
        this.sc = sc;
        this.manager = new BrowserHistory(); // no history loaded
    }

    /**
     * Saves current history
     */
    private void saveHistory() {
        if (this.manager == null) System.out.println("No history to save!");
        else {
            System.out.print("Do you want to save the last current history?(Y/N)");
            if ("yY".indexOf(this.sc.nextLine().charAt(0)) != -1) {
                BrowserHistory.saveHistory(this.manager);
                System.out.printf("Saved history %s....\n", this.manager.getDate());
            }
        }
    }

    /**
     * Edits the current playlist
     */
    private void editPlaylist() {
        boolean back = false;
        while (!back) {
            
            System.out.println("PLAYLIST EDIT MENU:");
            System.out.println("-----------------");
            System.out.println("\t1. Update Name");
            System.out.println("\t2. Update Creator Name");
            System.out.println("\t3. Change Mode");
            System.out.println("\t4. Edit Songs");
            System.out.println("\t0. Return to Main Menu");
    
            System.out.print("Enter your choice: ");
            int option = this.sc.nextInt(); this.sc.nextLine();
    
            String name = "", creator = ""; // strings that might be needed
    
            switch (option) {
                case 1: System.out.print("Enter the new name: ");
                    name = this.sc.nextLine();
                    this.manager.setName(name);
                    break;
                case 2: System.out.print("Enter the new creator's name: ");
                    creator = this.sc.nextLine();
                    this.manager.setCreator(creator);
                    break;
                case 3: this.changeMode();
                    break;
                case 4: this.editSongs(); break;
                case 0:
                default: back = true;
                    break;
            }
        }
        this.start();
    }

    public void changeMode() {
        System.out.println("Choose one of the modes: "); 
        System.out.println("\t1. Normal");
        System.out.println("\t2. Single-loop");
        System.out.println("\t3. Repeat");
        System.out.println("\t4. Shuffle");
        System.out.println("\t5. Reverse");

        System.out.println("Enter your choice: ");
        int choice = this.sc.nextInt(); this.sc.nextLine();
        this.manager.setMode(Mode.fromInt(choice));
    }

    /**
     * Edit songs of the current playlist
     */
    private void editSongs() {

        boolean back = false;

        while (!back) {
            
            System.out.println("SONG EDIT OPTIONS: ");
            System.out.println("-----------------");
            System.out.println("\t1. Add New Song");
            System.out.println("\t2. Remove Song");
            System.out.println("\t3. Edit Song");
            System.out.println("\t4. Change Order");
            System.out.println("\t0. Go to Playlist Menu");
    
            System.out.print("Enter your choice: ");
            int option = this.sc.nextInt(); this.sc.nextLine();
    
            String title = "", artist = "", duration = ""; // strings that might be needed
            long position = -1;
    
            switch (option) {
                case 1: System.out.print("Enter song title: ");
                    title = sc.nextLine();
                    System.out.print("Enter song artist name: ");
                    artist = sc.nextLine();
                    System.out.print("Enter song duration: ");
                    duration = sc.nextLine();
                    System.out.print("Do you want to enter it into a specific position?(Y/N):");
                    if ("yY".indexOf(this.sc.nextLine().charAt(0)) != -1) {
                        System.out.println("Enter the position: ");
                        position = sc.nextLong(); this.sc.nextLine();
                    }
                    this.manager.addSong(title, artist, duration, position);
                    break;
                case 2: System.out.print("Do you want to remove by song title?(Y/N):");
                    if ("yY".indexOf(this.sc.nextLine().charAt(0)) != -1) {
                        System.out.println("Enter song title: ");
                        title = sc.nextLine();
                    } else {
                        System.out.println("Enter song position: ");
                        position = sc.nextLong(); this.sc.nextLine();
                    }
                    this.manager.removeSong(title, position);
                    break;
                case 3: System.out.println("Enter song position: ");
                    position = sc.nextLong(); this.sc.nextLine();
                    System.out.print("Enter song title: ");
                    title = sc.nextLine();
                    System.out.print("Enter song artist name: ");
                    artist = sc.nextLine();
                    System.out.print("Enter song duration: ");
                    duration = sc.nextLine();
                    this.manager.updateSong(position, title, artist, duration);
                    break;
                case 4: System.out.print("Enter song title: ");
                    title = this.sc.nextLine();
                    System.out.print("Enter new position for the song: ");
                    position = this.sc.nextLong(); this.sc.nextLine();
                    this.manager.moveSong(title, position);
                    break;
                case 0: 
                default: back = true;
                    break;
            }
        }

        this.editPlaylist();
    }

    /**
     * Controls the display and running of the menu options
     * @return
     */
    public boolean mainMenu() {
        System.out.println("\n-------------------\nMAIN MENU\n-------------------\n");
        System.out.print("Current Page: ");
        if (this.manager == null) {
            System.out.println("<---NEW TAB--->.");
        } else {
            System.out.println(this.manager.getCurrentPage());
        }

        System.out.println("\t1. Visit new Page");
        System.out.println("\t2. Go Back");
        System.out.println("\t3. Go Forward");
        System.out.println("\t4. Undo Navigation");
        System.out.println("\t5. Redo Navigation");
        System.out.println("\t6. View History");
        System.out.println("\t7. Search History");
        System.out.println("\t8. Load Page");
        System.out.println("\t9. Remove Page");
        System.out.println("\t10. Clear History");
        System.out.println("\t11. Bookmark Current Page");
        System.out.println("\t12. Load Saved History");
        System.out.println("\t13. Save Currrent History\n");
        System.out.println("\t0. Exit");

        System.out.print("Enter your choice: ");
        int option = this.sc.nextInt(); this.sc.nextLine();

        String url = "", title = ""; // strings that might be needed
        List<String> tags = new ArrayList<>();

        switch (option) {
            case 1: System.out.print("Enter the page URL:"); url = sc.nextLine(); // getting the url
                System.out.print("Enter the page title:"); title = sc.nextLine(); // getting the title

                // all the tags
                System.out.print("Enter the tags associated (space separated):"); 
                tags.addAll(Arrays.asList(sc.nextLine().split(" ")));

                this.manager.visitNewPage(url, title, tags, false);
                break;
            case 2: this.manager.goBack();
                break;
            case 3: this.manager.goForward();
                break;
            case 4: 
                break;
            case 5: 
                break;
            case 6: 
                break;
            case 7: 
                break;
            case 8:
                break;
            case 9: 
                break;
            case 10: 
                break;
            case 11: 
                break;
            case 12: 
                break;
            case 13: 
                break;
            case 0:
            default: System.err.println("Exiting....");
                return false;
        }

        return true;
    }

    /**
     * Entry point for CLI app
     */
    public void start() {
        boolean run = true;
        System.out.println("\n\n=================================BROWSER HISTORY MANAGER - COMMAND LINE INTERFACE=================================\n\n");
        while (run) {
            run = mainMenu();
        }
    }
}
