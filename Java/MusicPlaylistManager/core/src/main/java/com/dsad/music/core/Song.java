package com.dsad.music.core;

import java.time.Duration;
import java.io.Serializable;

public class Song implements Comparable<Song>, Serializable {
    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private static final long serialVersionUID = 1L; // for versioning

    // instance members
    private String title, artist;
    private Duration duration;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Parameterized constructor
     * @param title
     * @param artist
     * @param duration
     */
    public Song(String title, String artist, String duration) {
        this.title = title;
        this.artist = artist;
        this.duration = parseTimeString(duration);
    }

    /**
     * Parse the time string provided to convert it to Duration
     * @param timeString
     * @return Duration object in seconds
     */
    public static Duration parseTimeString(String timeString) {
        String[] parts = timeString.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);

        return Duration.ofSeconds(hours * 3600 + minutes * 60 + seconds);
    }

    /**
     * Getter for duration
     * @return formated duration string
     */
    public static String getDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Getter for title
     * @return title of the song
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for artist
     * @return artist of the song
     */
    public String getArtist() {
        return this.artist;
    }

    /**
     * For easy printing
     */
    @Override
    public String toString() {
        return String.format("%s by %s - %s", this.title, this.artist, getDuration(this.duration));
    }

    /**
     * For comparing two Song objects
     * @param s
     * @return comparison result according to title, artist, duration
     */
    @Override
    public int compareTo(Song s) {
        int result = -2;
        if (this.title.equalsIgnoreCase(s.getTitle()) || this.artist.equalsIgnoreCase(s.getArtist())) {
            result = 0;
        } else if (this.duration.compareTo(s.duration) != 0) {
            result = this.duration.compareTo(s.duration);
        }
        return result;
    }
    
}
