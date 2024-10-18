package com.dsad.music.core;

import java.time.Duration;

public class PlaylistManager {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private DoublyLinkedList<Song> playlist; // actual playlist
    private Duration totalDuration; // keeping track of total duration
    private String name, creator, mode; // normal, single-loop, repeat, shuffle, reverse

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Creating the playlist
     * @param name playlist title
     * @param creator who created the playlist
     */
    public PlaylistManager(String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.playlist = new DoublyLinkedList<>();
        this.totalDuration = Duration.ofSeconds(0);
        this.mode = "Normal";
    }
    
    /**
     * Adding a new song to the playlist
     * @param title
     * @param artist
     * @param duration
     * @param position
     */
    public void addSong(String title, String artist, String duration, long position) {
        Song newSong = new Song(title, artist, duration); // new song object
        this.playlist.insert(newSong, position); // adding song to the playlist

        // adding song duration to the total duration
        this.totalDuration = this.totalDuration.plus(Song.parseTimeString(duration));

        System.out.println(newSong + " was added to " + this.name);
    }

    /**
     * Removing a song from the playlist based on the title or position in the playlist
     * @param title
     * @param position
     */
    public void removeSong(String title, long position) {
        boolean result = false;

        if (!title.equals("")) { // if title given remove by title first
            Song song = new Song(title, "", "::"); // just a blank song
            // deleting the song
            result = this.playlist.deleteNode(song);
        } else if (position >= 1) {
            result = this.playlist.deleteNode(Long.valueOf(position));
        }

        System.out.printf("%s %s from %s\n", 
            ((result)? "Deleted": "Could not delete"),
            ((title.equals("")? "song number " + position: title)),
            this.name
        );
    }

    /**
     * Display the playlist
     */
    public void displayPlaylist() {
        System.out.println(this);
    }

    /**
     * For easy printing
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("Name:\t\t").append(this.name).append("\n");
        s.append("Created By:\t\t").append(this.creator).append("\n");
        s.append("Total Duration:\t\t").append(Song.getDuration(this.totalDuration)).append("\n");
        s.append("Songs:\n").append("=============================================================").append("\n\n");
        s.append(this.playlist);
        s.append("\n").append("=============================================================").append("\n");
        s.append("Mode: ").append(this.mode).append("\n\n");

        return s.toString();
    }

    /**
     * Sets a new mode and makes necessary changes
     * @param newMode
     */
    public void setMode(String newMode) {
        this.mode = newMode;
        if (this.mode.equalsIgnoreCase("reverse")) {
            System.out.println("Reversing the playlist.....");
            this.playlist.reverseList();
        } else if (this.mode.equalsIgnoreCase("shuffle")) {
            System.out.println("Shuffling all songs.....");
            reorderAllSongs();
        }
    }

    /**
     * Shuffles the playlist based on random indices
     */
    public void reorderAllSongs() {        
        long min = 1, max = this.playlist.getSize(); // range
        
        for (long i = min; i <= max; i++) {
            // find a new random index
            long newIndex = min + (long)(Math.random() * (max - min));
            this.playlist.moveNode(i, newIndex);
        }
    }

    /**
     * Moves a song from one position to another
     * @param title
     * @param newPosition
     */
    public void moveSong(String title, long newPosition) {
        if (newPosition >= 1 && newPosition <= this.playlist.getSize()) {
            Song song = new Song(title, "", ":");
            this.playlist.moveNode(song, newPosition);

            System.out.printf("Moved %s to %d in %s\n", title, newPosition, this.name);
        }
    }

    /**
     * Search a song in the playlist and display search result
     * @param title
     * @param artist
     */
    public void searchSong(String title, String artist) {
        Song song = new Song(title, artist, ":");
        long index = this.playlist.indexOf(song);

        System.out.printf("%s %s in %s%s\n",
            ((index == -1)? "Not found": "Found"),
            song,
            this.name,
            ((index == -1)? ".": "at " + index + ".")
        );
    }

}
