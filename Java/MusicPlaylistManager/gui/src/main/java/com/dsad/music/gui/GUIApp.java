package com.dsad.music.gui;

import com.dsad.music.core.PlaylistManager;
import com.dsad.music.core.Song;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIApp extends Application {


    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private PlaylistManager manager; // core logic

    // GUI elements
    private Scene scene;

    private VBox playlistInfo, nowPlaying;
    private HBox playlistName, creatorName, totalDuration;
    private ListView<Song> songListView;


    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */

    public GUIApp() {
        //playlist details
        this.playlistInfo = new VBox(10);

        // playlist name
        this.playlistName = new HBox(5);
        Label nameLabel = new Label("Playlist: ");
        nameLabel.getStyleClass().add("label-text");
        Label nameContent = new Label((this.manager == null)? "No playlist loaded.": this.manager.getName());
        nameContent.getStyleClass().add("content-text");
        this.playlistName.getChildren().addAll(nameLabel, nameContent);

        // creator name
        this.creatorName = new HBox(5);
        Label artistLabel = new Label("Created By: ");
        artistLabel.getStyleClass().add("label-text");
        Label artistContent = new Label((this.manager == null)? "": this.manager.getCreator());
        artistContent.getStyleClass().add("content-text");
        this.creatorName.getChildren().addAll(artistLabel, artistContent);

        // total duration
        this.totalDuration = new HBox(5);
        Label durationLabel = new Label("Total Duration: ");
        durationLabel.getStyleClass().add("label-text");
        Label durationContent = new Label((this.manager == null)? "": Song.getDuration(this.manager.getTotalDuration()));
        durationContent.getStyleClass().add("content-text");
        this.totalDuration.getChildren().addAll(durationLabel, durationContent);

        this.playlistInfo.getChildren().addAll(this.playlistName, this.creatorName, this.totalDuration); // adding playlist details

        // now playing
        this.nowPlaying = new VBox(10);
        // Label songLabel = new Label("NOW PLAYING");
        // songLabel.getStyleClass().add("header-label");
        Song currentSong = (this.manager == null)? null: this.manager.getCurrentSongData();
        Label songNameLabel = new Label("Title:" + ((currentSong == null)?"":currentSong.getTitle()));
        Label songArtistLabel = new Label("Artist:" + ((currentSong == null)?"":currentSong.getArtist()));
        Label songDurationLabel = new Label("Duration:" + ((currentSong == null)?"":Song.getDuration(currentSong.getDuration())));

        this.nowPlaying.getChildren().addAll(songNameLabel, songArtistLabel, songDurationLabel);
        this.nowPlaying.getStyleClass().add("now-playing-box");


    }

    @Override
    public void start(Stage primaryStage) {
        this.manager = null;
        
        primaryStage.setTitle("Music Playlist Manager"); // main window

        // Main top vertical box to show all details
        VBox root = new VBox();
        root.setPadding(new Insets(20, 20, 20, 20)); // 10px padding on all sides
        
        TitledPane nowPlayingPane = new TitledPane("NOW PLAYING", this.nowPlaying);

        root.getChildren().addAll(playlistInfo, nowPlayingPane);

        this.scene = new Scene(root, 600, 600);
        this.scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
