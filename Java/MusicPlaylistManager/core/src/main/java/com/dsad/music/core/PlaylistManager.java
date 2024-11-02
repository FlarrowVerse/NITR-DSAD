package com.dsad.music.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;


public class PlaylistManager implements Serializable {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private static final long serialVersionUID = 1L; // for versioning

    private DoublyLinkedList<Song> playlist; // actual playlist
    private Duration totalDuration; // keeping track of total duration
    private String name, creator; // normal, single-loop, repeat, shuffle, reverse
    private Mode mode;

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
        this.mode = Mode.NORMAL;
    }

    /**
     * Getter for playlist name
     * @return playlist name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for creator
     * @return creator's name
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * Getter for mode
     * @return current mode
     */
    public Mode getMode() {
        return this.mode;
    }

    /**
     * Getter for total duration
     * @return Duration object
     */
    public Duration getTotalDuration() {
        return this.totalDuration;
    }

    /**
     * Get the current song in the playlist
     * @return current song as string
     */
    public String getCurrentSong() {
        Song currentSong = this.playlist.getCurrentNode();
        return (currentSong == null)? "": currentSong.toString();
    }

    /**
     * Get the current song in the playlist
     * @return current song
     */
    public Song getCurrentSongData() {
        return this.playlist.getCurrentNode();
    }

    /**
     * Get all the songs as a list
     * @return list of Song objects
     */
    public List<Song> getAllSongs() {
        List<Song> songs = this.playlist.getAllNodeData();
        return songs;
    }

    /**
     * Moves back the current pointer in the linked list
     */
    public void goBack() {
        if (this.mode == Mode.SOLOLOOP) return; // if on a single loop do not change the song
        this.playlist.moveBack(this.mode == Mode.REPEAT);
        this.playlist.getCurrentNode();
    }

    /**
     * Moves forward the current pointer in the linked list
     */
    public void goForward() {
        if (this.mode == Mode.SOLOLOOP) return; // if on a single loop do not change the song
        this.playlist.moveForward(this.mode == Mode.REPEAT);
        this.playlist.getCurrentNode();
    }


    /**
     * Setter for name
     * @param name playlist name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for creator
     * @param creator playlist creator name
     */
    public void setCreator(String creator) {
        this.creator = creator;
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
        if (position == -1) this.playlist.insert(newSong, this.playlist.getSize());
        else this.playlist.insert(newSong, position); // adding song to the playlist

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
        Song result = null;

        if (!title.equals("")) { // if title given remove by title first
            // deleting the song
            result = this.playlist.deleteNode(-1, song -> song.getTitle().equals(title));
        } else if (position >= 1 && position <= this.playlist.getSize()) {
            result = this.playlist.deleteNode(Long.valueOf(position), null);
        }

        this.totalDuration = (result == null)? this.totalDuration: this.totalDuration.minus(result.getDuration());

        System.out.printf("%s %s from %s\n", 
            ((result != null)? "Deleted": "Could not delete"),
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
    public void setMode(Mode newMode) {
        this.mode = newMode;
        if (this.mode == Mode.REVERSE) {
            System.out.println("Reversing the playlist.....");
            this.playlist.reverseList();
        } else if (this.mode == Mode.SHUFFLE) {
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
            this.playlist.moveNode(i, newIndex, null);
        }
    }

    /**
     * Moves a song from one position to another
     * @param title
     * @param newPosition
     */
    public boolean moveSong(String title, long newPosition) {
        if (title.isEmpty() || (newPosition >= 1 && newPosition <= this.playlist.getSize())) {
            this.playlist.moveNode(-1, newPosition, song -> song.getTitle().equals(title));
            System.out.printf("Moved %s to %d in %s\n", title, newPosition, this.name);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Search a song in the playlist and display search result
     * @param title
     * @param artist
     */
    public long searchSong(String title, String artist) {
        if (title.isEmpty() && artist.isEmpty()) {
            return -1;
        }

        long index = this.playlist.indexOf(song -> song.getTitle().equalsIgnoreCase(title) || song.getArtist().equalsIgnoreCase(artist));

        System.out.printf("%s %s in %s%s\n",
            ((index == -1)? "Not found": "Found"),
            (title.length() == 0)? artist: title,
            this.name,
            ((index == -1)? ".": " at " + index + ".")
        );

        return index;
    }

    /**
     * Updates a single song
     * @param position
     * @param title
     * @param artist
     * @param duration
     * @return true/false
     */
    public boolean updateSong(long position, String title, String artist, String duration) {
        if (position == -1 && title.isEmpty() && artist.isEmpty() && duration.isEmpty()) {
            return false;
        }
        
        Song song = this.playlist.getNodeData(position);
        if (!title.isEmpty()) {
            song.setTitle(title);
        }

        if (!artist.isEmpty()) {
            song.setArtist(artist);
        }

        if (!duration.isEmpty()) {
            Duration prevDuration = song.getDuration();
            song.setDuration(duration);
            this.totalDuration = this.totalDuration.minus(prevDuration).plus(song.getDuration()); // update the totalDuration
        }
        return true;
    }

    public void sort(boolean byTitle) {
        Comparator<Song> comparator = null;
        if (byTitle) {
            comparator = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());
        } else {
            comparator = (Song s1, Song s2) -> s1.getArtist().compareTo(s2.getArtist());
        }

        this.playlist.sort(comparator); // sort the playlist
    }

    /**
     * Handles filename fetch
     * @param filename
     * @return full path of the playlist file
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
     * Save the given playlist
     * @param playlistManager object to be saved
     * @param basePath resources directory path
     */
    public static boolean savePlaylist(PlaylistManager playlistManager) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(playlistManager.getName())))) {
            oos.writeObject(playlistManager);
            System.out.println(playlistManager.getName() + " successfully saved to file.");
            return true;
        } catch (IOException e) {
            System.err.println("ERROR: Could not save " + playlistManager.getName());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Load a playlist given its name
     * @param playlistName playlist to be loaded
     * @return PlaylistManager object
     */
    public static PlaylistManager loadPlaylist(String playlistName) {
        PlaylistManager manager = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFilePath(playlistName)))) {
            manager = (PlaylistManager) ois.readObject();
            manager.playlist.setCurrent(null, 1); // setting the first song as the current
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR: Could not load " + playlistName);
            e.printStackTrace();
        }        

        return manager;
    }

    /**
     * Delete a playlist given its name
     * @param playlistName playlist to be deleted
     */
    public static boolean deletePlaylist(String playlistName) {
        File file = new File(getFilePath(playlistName));

        if (file.exists()) {
            return file.delete();
        }
        System.err.println("File not found!");
        return false;
    }

    /**
     * Removes all songs from the current playlist
     * @return
     */
    public boolean clear() {
        if (this.playlist != null) {
            this.playlist.clear();
            this.totalDuration = Duration.ofSeconds(0);
            return true;
        }
        return false;
    }

}
