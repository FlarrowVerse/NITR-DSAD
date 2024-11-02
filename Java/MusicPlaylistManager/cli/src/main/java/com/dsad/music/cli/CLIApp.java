package com.dsad.music.cli;

import java.util.Scanner;

import com.dsad.music.core.Mode;
import com.dsad.music.core.PlaylistManager;

public class CLIApp {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private Scanner sc; // for input
    private PlaylistManager manager;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Parameterized constructor
     * @param sc
     */
    public CLIApp(Scanner sc) {
        this.sc = sc;
        this.manager = null; // no playlist loaded
    }

    /**
     * Saves current playlist
     */
    private void savePlaylist() {
        if (this.manager == null) System.out.println("No playlist to save!");
        else {
            System.out.print("Do you want to save the last current playlist?(Y/N)");
            if ("yY".indexOf(this.sc.nextLine().charAt(0)) != -1) {
                PlaylistManager.savePlaylist(this.manager);
                System.out.printf("Saved playlist %s....\n", this.manager.getName());
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
            System.out.println("\t5. Clear Playlist");
            System.out.println("\t6. Sort Playlist");
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
                case 5: this.manager.clear(); break;
                case 6: System.out.print("Do you want to sort by title? (Y=title/N=artist): ");
                    this.manager.sort("yY".indexOf(sc.nextLine()) != -1);
                    break;
                case 0:
                default: back = true;
                    break;
            }
            System.out.println("\n");
        }
    }

    public void changeMode() {
        System.out.println("Choose one of the modes: "); 
        System.out.println("\t1. Normal");
        System.out.println("\t2. Shuffle");
        System.out.println("\t3. Repeat");
        System.out.println("\t4. Single Loop");
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
            System.out.println("\t5. Search for song");
            System.out.println("\t0. Go to Playlist Menu");
    
            System.out.print("Enter your choice: ");
            int option = this.sc.nextInt(); this.sc.nextLine();
    
            String title = "", artist = "", duration = "", line = ""; // strings that might be needed
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
                case 2: System.out.println("Enter song title (default ''): ");
                    title = sc.nextLine();
                    System.out.println("Enter song position (default -1): ");
                    line = sc.nextLine(); position = (line.isEmpty()) ? -1 : Long.parseLong(line);
                    this.manager.removeSong(title, position);
                    break;
                case 3: System.out.println("Enter song position (default -1): ");
                    line = sc.nextLine(); position = (line.isEmpty()) ? -1 : Long.parseLong(line);
                    System.out.print("Enter song title (default ''): ");
                    title = sc.nextLine();
                    System.out.print("Enter song artist name (default ''): ");
                    artist = sc.nextLine();
                    System.out.print("Enter song duration (default ''): ");
                    duration = sc.nextLine();
                    this.manager.updateSong(position, title, artist, duration);
                    break;
                case 4: System.out.print("Enter song title (default ''): ");
                    title = this.sc.nextLine();
                    System.out.print("Enter new position for the song (default -1): ");
                    line = sc.nextLine(); position = (line.isEmpty()) ? -1 : Long.parseLong(line);
                    this.manager.moveSong(title, position);
                    break;
                case 5: System.out.println("Enter song title (default ''): ");
                    title = sc.nextLine();
                    System.out.println("Enter artist name (default ''): ");
                    artist = sc.nextLine();
                    this.manager.searchSong(title, artist);
                    break;
                case 0: 
                default: back = true;
                    break;
            }
            System.out.println("\n");
        }
    }

    /**
     * Controls the display and running of the menu options
     * @return
     */
    public boolean mainMenu() {
        System.out.println("\n\n=================================MUSIC PLAYLIST MANAGER - COMMAND LINE INTERFACE=================================\n\n");
        System.out.print("SHOWING PLAYLIST: ");
        if (this.manager == null) {
            System.out.println("No playlist loaded.");
        } else {
            System.out.println(this.manager);
            showCurrentSong();
        }
        
        System.out.println("PLAYLIST OPTIONS: ");
        System.out.println("-----------------");
        System.out.println("\t1. Create New Playlist");
        System.out.println("\t2. Delete Playlist");
        System.out.println("\t3. Edit Playlist");
        System.out.println("\t4. Load Playlist");
        System.out.println("\t5. Save Playlist\n");

        System.out.println("PLAYER OPTIONS: ");
        System.out.println("-----------------");
        System.out.println("\t6. Go to next song");
        System.out.println("\t7. Go to previous song\n");

        System.out.println("\t0. Exit");

        System.out.print("Enter your choice: ");
        int option = this.sc.nextInt(); this.sc.nextLine();

        String name = "", creator = ""; // strings that might be needed

        switch (option) {
            case 1: this.savePlaylist(); // saves current playlist based on user's choice
                System.out.print("Enter new playlist name: ");
                name = sc.nextLine();
                System.out.print("Enter new playlist's creator's name: ");
                creator = sc.nextLine();
                this.manager = new PlaylistManager(name, creator); // new playlist creator
                break;
            case 2: System.out.print("Enter the name of the playlist to be deleted: ");
                name = sc.nextLine();
                if (!PlaylistManager.deletePlaylist(name)) {
                    System.err.println("Could not delete the playlist you mentioned.");
                } else {
                    System.out.println("Delete playlist " + name);
                }
                break;
            case 3:
                this.editPlaylist();
                break;
            case 4: savePlaylist();
                System.out.print("Enter the name of the playlist to be loaded: ");
                name = sc.nextLine();
                PlaylistManager temp = PlaylistManager.loadPlaylist(name);
                if (temp == null) {
                    System.err.println("Could not load the playlist you mentioned.");
                }
                this.manager = (temp != null)? temp: this.manager; // if loaded set it as current playlist
                break;
            case 5: savePlaylist();
                break;
            case 6: this.manager.goForward(); // move to next song
                break;
            case 7: this.manager.goBack(); // move to previous song
                break;
            case 0:
            default: System.err.println("Exiting....");
                return false;
        }

        return true;
    }

    private void showCurrentSong() {
        String songData = this.manager.getCurrentSong().toString();
        int songLength = (songData.length() == 0)? "NO SONG PLAYING".length(): songData.length();        
        int boxWidth = Math.max(songLength + 4, "NOW PLAYING".length() + 4);
        int headerPad = (boxWidth - "NOW PLAYING".length()) / 2;
        int contentPad = ((boxWidth - songLength) / 2) - 1;
        
        // Create the top border with "NOW PLAYING" at the start
        String topBorder = "*".repeat(headerPad) + "NOW PLAYING" + "*".repeat(headerPad);

        String content = "*" + " ".repeat(contentPad) + ((songData.length() == 0) ? "NO SONG PLAYING" : songData)
                + " ".repeat(contentPad) + "*";

        // Create the bottom border and print the message inside the box
        String bottomBorder = "*".repeat(boxWidth);
        
        System.out.println(topBorder);
        System.out.println(content);
        System.out.println(bottomBorder);
    }

    /**
     * Entry point for CLI app
     */
    public void start() {
        boolean run = true;

        while (run) {
            run = mainMenu();
            System.out.println("\n");
        }
    }
}
