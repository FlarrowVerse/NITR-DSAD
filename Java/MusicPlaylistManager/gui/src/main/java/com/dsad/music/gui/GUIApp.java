package com.dsad.music.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dsad.music.core.Mode;
import com.dsad.music.core.PlaylistManager;
import com.dsad.music.core.Song;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIApp extends Application {


    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private PlaylistManager manager; // core logic

    // GUI elements
    private Scene scene;

    private VBox playlistInfo, nowPlaying;
    private Label currentPlaylistName, currentPlaylistCreator, currentPlaylistDuration, currentSongName,
            currentSongArtist, currentSongDuration;
    private HBox playlistName, creatorName, totalDuration, mode;
    private Button editSong, deleteSong, moveSong, nextSong, previousSong, addSong, searchSong; // song related buttons
    private Button loadPlaylist, deletePlaylist, editPlaylist, sortPlaylist, createPlaylist, savePlaylist, showPlaylist,
            clearPlaylist; // playlist buttons
    private ComboBox<String> modeComboBox;


    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */

    /**
     * Default constructor called automatically
     */
    public GUIApp() {
        initInfoDisplay();
        initButtons();
    }

    /**
     * Initializes the buttons and their actions
     */
    private void initButtons() {
        /** Playlist related buttons */
        this.createPlaylist = new Button("+ New");
        this.createPlaylist.setOnAction(e -> this.createNewPlaylist());
        
        this.deletePlaylist = new Button("- Delete");
        this.deletePlaylist.setOnAction(e -> this.deletePlaylist());

        this.clearPlaylist = new Button("Clear");
        this.clearPlaylist.setOnAction(e -> this.clearPlaylist());

        this.editPlaylist = new Button("Edit");
        this.editPlaylist.setOnAction(e -> this.editCurrentPlaylist());

        this.sortPlaylist = new Button("Sort");
        this.sortPlaylist.setOnAction(e -> this.sortCurrentPlaylist());

        this.showPlaylist = new Button("Show");
        this.showPlaylist.setOnAction(e -> this.showCurrentPlaylist());

        this.loadPlaylist = new Button("--> Load");
        this.loadPlaylist.setOnAction(e -> this.loadPlaylist());

        this.savePlaylist = new Button("<-- Save");
        this.savePlaylist.setOnAction(e -> this.saveCurrentPlaylist());

        /** Song related buttons */
        this.addSong = new Button("+ New");
        this.addSong.setOnAction(e -> this.addNewSong());
        
        this.deleteSong = new Button("- Delete");
        this.deleteSong.setOnAction(e -> this.deleteSong());

        this.editSong = new Button("Edit");
        this.editSong.setOnAction(e -> this.editSong());

        this.nextSong = new Button(">>>");
        this.nextSong.setOnAction(e -> this.goForward());

        this.searchSong = new Button("Search");
        this.searchSong.setOnAction(e -> this.searchSong());

        this.previousSong = new Button("<<<");
        this.previousSong.setOnAction(e -> this.goBack());

        this.moveSong = new Button("<-> Move");
        this.moveSong.setOnAction(e -> this.moveSong());
    }

    /**
     * This initializes the display for the current song and playlist details
     */
    public void initInfoDisplay() {
        //playlist details
        this.playlistInfo = new VBox(10);
        
        // playlist name
        // all containers
        this.playlistName = new HBox(5);
        this.creatorName = new HBox(5);
        this.totalDuration = new HBox(5);
        this.mode = new HBox(5);

        // all labels
        Label nameLabel = new Label("Playlist: ");
        nameLabel.getStyleClass().add("label-text");
        Label artistLabel = new Label("Created By: ");
        artistLabel.getStyleClass().add("label-text");
        Label durationLabel = new Label("Total Duration: ");
        durationLabel.getStyleClass().add("label-text");
        Label modeLabel = new Label("Mode: ");
        durationLabel.getStyleClass().add("label-text");

        // playlist name
        this.currentPlaylistName = new Label((this.manager == null)? "No playlist loaded.": this.manager.getName());
        this.currentPlaylistName.getStyleClass().add("content-text");
        // creator name
        this.currentPlaylistCreator = new Label((this.manager == null)? "": this.manager.getCreator());
        this.currentPlaylistCreator.getStyleClass().add("content-text");
        // total duration
        this.currentPlaylistDuration = new Label((this.manager == null)? "": Song.getDuration(this.manager.getTotalDuration()));
        this.currentPlaylistDuration.getStyleClass().add("content-text");
        // mode
        this.modeComboBox = new ComboBox<>();
        this.modeComboBox.getItems().addAll("Normal", "Shuffle", "Repeat", "Single Loop", "Reverse");
        this.modeComboBox.setValue(Mode.toString((this.manager == null)? Mode.NORMAL: this.manager.getMode()));
        this.modeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle the mode change logic here
                this.manager.setMode(Mode.fromInt(this.modeComboBox.getItems().indexOf(newValue) + 1));
            }
            updateLabels();
        });
        // adding all elements to the containers
        this.playlistName.getChildren().addAll(nameLabel, this.currentPlaylistName);
        this.creatorName.getChildren().addAll(artistLabel, this.currentPlaylistCreator);
        this.totalDuration.getChildren().addAll(durationLabel, this.currentPlaylistDuration);
        this.mode.getChildren().addAll(modeLabel, this.modeComboBox);
        // adding playlist details
        this.playlistInfo.getChildren().addAll(this.playlistName, this.creatorName, this.totalDuration, this.mode); 
        
        
        
        
        // now playing
        this.nowPlaying = new VBox(20);
        Song currentSong = (this.manager == null)? null: this.manager.getCurrentSongData();

        // all labels
        Label songNameLabel = new Label("Title:");
        songNameLabel.getStyleClass().add("label-text");
        Label songArtistLabel = new Label("Artist:");
        songNameLabel.getStyleClass().add("label-text");
        Label songDurationLabel = new Label("Duration:");
        songNameLabel.getStyleClass().add("label-text");

        // all elements
        this.currentSongName = new Label(((currentSong == null)?"":currentSong.getTitle()));
        this.currentSongName.getStyleClass().add("content-text");
        this.currentSongArtist = new Label(((currentSong == null)?"":currentSong.getArtist()));
        this.currentSongArtist.getStyleClass().add("content-text");
        this.currentSongDuration = new Label(((currentSong == null)?"":Song.getDuration(currentSong.getDuration())));
        this.currentSongDuration.getStyleClass().add("content-text");

        this.nowPlaying.getChildren().addAll(new HBox(5, songNameLabel, this.currentSongName),
                new HBox(5, songArtistLabel, this.currentSongArtist),
                new HBox(5, songDurationLabel, this.currentSongDuration));
        this.nowPlaying.getStyleClass().add("section-box");

    }

    /**
     * Updates the labels
     */
    private void updateLabels() {
        // current playlist details
        if (this.manager != null) {
            this.currentPlaylistName.setText(this.manager.getName());
            this.currentPlaylistCreator.setText(this.manager.getCreator());
            this.currentPlaylistDuration.setText(Song.getDuration(this.manager.getTotalDuration()));
            this.modeComboBox.setValue(Mode.toString(this.manager.getMode()));
    
            if (this.manager.getCurrentSongData() != null) {
                this.udpateSongLabels();
            }
        }
    }

    /**
     * Updates the current song label only
     */
    private void udpateSongLabels() {
        // current song details
        Song currentSong = this.manager.getCurrentSongData();
        this.currentSongName.setText(currentSong.getTitle());
        this.currentSongArtist.setText(currentSong.getArtist());
        this.currentSongDuration.setText(Song.getDuration(currentSong.getDuration()));
    }


    /**
     * Moves the current song in the queue
     */
    private void moveSong() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Move a Song");
        dialog.setHeaderText("Enter data in all the fields");

        ButtonType submitButtonType = new ButtonType("Move", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField title = new TextField();
        title.setPromptText("Song Title: ");
        TextField position = new TextField();
        position.setPromptText("Song Position: ");

        VBox content = new VBox(10, title, position);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Move" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("title", title.getText());
                inputs.put("position", position.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            String posStr = result.get().get("position");
            this.manager.moveSong(result.get().get("title"), (posStr.isEmpty())? -1: Long.parseLong(posStr));
        }
    }

    /**
     * Moves to the previous song in the queue
     */
    private void goBack() {
        if (this.manager != null) {
            this.manager.goBack();
            this.udpateSongLabels();
        }
    }

    /**
     * Searches for a song
     */
    private void searchSong() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Search playlist");
        dialog.setHeaderText("Enter data in required fields");

        ButtonType submitButtonType = new ButtonType("Search", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField title = new TextField();
        title.setPromptText("Enter Song Title...");
        TextField artist = new TextField();
        artist.setPromptText("Enter Artist Name...");

        VBox content = new VBox(10, title, artist);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Search" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("title", title.getText());
                inputs.put("artist", artist.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {            
            String titleStr = result.get().get("title");
            String artistStr = result.get().get("artist");

            long position = this.manager.searchSong(titleStr, artistStr);

            if (position == -1) {
                this.showErrorAlert("Not found the song you are looking for.");
            } else {
                this.showInfoAlert("Found the song you are looking for at position " + position);
            }
        }
    }

    /**
     * Moves to the next song in the queue
     */
    private void goForward() {
        if (this.manager != null) {
            this.manager.goForward();
            this.udpateSongLabels();
        }
    }

    /**
     * Edits a song
     */
    private void editSong() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit a song");
        dialog.setHeaderText("Enter data in required fields");

        ButtonType submitButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField title = new TextField();
        title.setPromptText("Enter Song Title...");
        TextField artist = new TextField();
        artist.setPromptText("Enter Artist Name...");
        TextField duration = new TextField();
        duration.setPromptText("Enter Song Duration...");
        TextField position = new TextField();
        position.setPromptText("Enter position you want to insert into...");

        VBox content = new VBox(10, title, artist, duration, position);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Move" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("title", title.getText());
                inputs.put("artist", artist.getText());
                inputs.put("duration", duration.getText());
                inputs.put("position", position.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            
            String posStr = result.get().get("position");
            String titleStr = result.get().get("title");
            String artistStr = result.get().get("artist");
            String durationStr = result.get().get("duration");

            this.manager.updateSong((posStr.isEmpty())? -1: Long.parseLong(posStr), titleStr, artistStr, durationStr);
        }

        this.updateLabels();
    }

    /**
     * Deletes a song from the playlist
     */
    private void deleteSong() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Remove a song");
        dialog.setHeaderText("Enter data in required fields");

        ButtonType submitButtonType = new ButtonType("Remove", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField title = new TextField();
        title.setPromptText("Enter Song Title...");
        TextField position = new TextField();
        position.setPromptText("Enter song position you want to remove...");

        VBox content = new VBox(10, title, position);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Move" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("title", title.getText());
                inputs.put("position", position.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            String posStr = result.get().get("position");
            String titleStr = result.get().get("title");

            this.manager.removeSong(titleStr, (posStr.isEmpty())? -1: Long.parseLong(posStr));
        }

        this.updateLabels();
    }

    /**
     * Adds a new song to the current playlist
     */
    private void addNewSong() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add a New Song");
        dialog.setHeaderText("Enter data in all the fields");

        ButtonType submitButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField title = new TextField();
        title.setPromptText("Enter Song Title...");
        TextField artist = new TextField();
        artist.setPromptText("Enter Artist Name...");
        TextField duration = new TextField();
        duration.setPromptText("Enter Song Duration...");
        TextField position = new TextField();
        position.setPromptText("Enter position you want to insert into...");

        VBox content = new VBox(10, title, artist, duration, position);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Move" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("title", title.getText());
                inputs.put("artist", artist.getText());
                inputs.put("duration", duration.getText());
                inputs.put("position", position.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            String posStr = result.get().get("position");
            this.manager.addSong(result.get().get("title"), result.get().get("artist"), result.get().get("duration"),
                    (posStr.isEmpty()) ? -1 : Long.parseLong(posStr));
        }

        this.updateLabels();
    }

    /**
     * Saves the current playlist
     */
    private void saveCurrentPlaylist() {
        if (this.manager != null && this.showConfirmAlert("Save the current playlist?")) {
            if (PlaylistManager.savePlaylist(this.manager)) {
                this.showInfoAlert(this.manager.getName() + " was successfully saved.");
            } else {
                this.showErrorAlert(this.manager.getName() + " could not be saved.");
            }
        }
    }


    /**
     * Loads the playlist
     */
    private void loadPlaylist() {
        // save the current playlist first
        boolean reload = false;
        if (this.manager != null) {
            reload = true;
            saveCurrentPlaylist();
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Playlist Prompt");
        dialog.setHeaderText("Enter name of the playlist to be loaded");
        dialog.setContentText("Playlist Name:");
        
        while (reload || this.manager == null) {
            reload = false;
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (result.get().trim().isEmpty()) {
                    showErrorAlert("Input cannot be empty. Please enter a valid value.");
                } else {
                    this.manager = PlaylistManager.loadPlaylist(result.get());
                }
            } else {
                break;
            }
        }
        this.updateLabels(); // updates the data
    }

    /**
     * Shows the entire current playlist
     */
    @SuppressWarnings("unchecked")
    private void showCurrentPlaylist() {
        if (manager != null) {
            List<Song> songs = this.manager.getAllSongs(); // Get songs from PlaylistManager
            ObservableList<Song> songData = FXCollections.observableArrayList(songs);

            // Create a new TableView and set items
            TableView<Song> popUpTableView = new TableView<>();
            popUpTableView.setItems(songData);

            // Create columns for the pop-up TableView
            TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
            artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));

            TableColumn<Song, String> durationColumn = new TableColumn<>("Duration");
            durationColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(Song.getDuration(cellData.getValue().getDuration()));
            });

            // Add columns to the TableView
            popUpTableView.getColumns().addAll(titleColumn, artistColumn, durationColumn);
            popUpTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);


            // Create a layout and add the TableView
            VBox popUpLayout = new VBox(10);
            popUpLayout.getChildren().add(popUpTableView);

            // Create a new Scene and Stage for the pop-up
            Scene popUpScene = new Scene(popUpLayout, 600, 400);
            popUpScene.getStylesheets().add("/css/styles.css");

            Stage popUpStage = new Stage();
            popUpStage.setTitle("Playlist Viewer");
            popUpStage.setScene(popUpScene);

            // Show the pop-up Stage
            popUpStage.show();
        } else {
            // Optional: show an alert if no playlist is available
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Playlist Available");
            alert.setContentText("Please load a playlist before attempting to view it.");
            alert.showAndWait();
        }
    }

    /**
     * Edits the current playlist
     */
    private void editCurrentPlaylist() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Current Playlist");
        dialog.setHeaderText("Enter data in all required fields");

        ButtonType submitButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField name = new TextField();
        name.setPromptText("Enter new Playlist Name...");
        TextField creator = new TextField();
        creator.setPromptText("Enter creator Name...");

        VBox content = new VBox(10, name, creator);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Save" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("name", name.getText());
                inputs.put("creator", creator.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            String nameVal = result.get().get("name");
            String creatorVal = result.get().get("creator");
            this.manager = new PlaylistManager((nameVal.isEmpty()) ? this.manager.getName() : nameVal,
                    (creatorVal.isEmpty()) ? this.manager.getCreator() : creatorVal);
        }

        this.updateLabels();
    }

    /**
     * Deletes the current playlist file
     */
    private void deletePlaylist() {
        if (this.manager != null && this.showConfirmAlert("Delete the current playlist?")) {
            String name = this.manager.getName();
            if (PlaylistManager.deletePlaylist(name)) {
                this.showInfoAlert(name + " was successfully deleted.");
            } else {
                this.showErrorAlert(name + " could not be deleted.");
            }
        }
    }

    /**
     * Sorts the playlist by Title or artist
     */
    private void sortCurrentPlaylist() {
        if (this.manager != null) {
            this.manager.sort(this.showConfirmAlert("Sort by Title?"));
        }
    }

    /**
     * Deletes the current playlist file
     */
    private void clearPlaylist() {
        if (this.manager != null && this.showConfirmAlert("Clear the current playlist?")) {
            String name = this.manager.getName();
            if (this.manager.clear()) {
                this.showInfoAlert(name + " was successfully deleted.");
            } else {
                this.showErrorAlert(name + " could not be deleted.");
            }
        }
    }

    /**
     * Creates a new playlist
     */
    private void createNewPlaylist() {
        if (this.manager != null) {
            saveCurrentPlaylist(); // saving the current playlist if any
        }

        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create a New Playlist");
        dialog.setHeaderText("Enter data in all the fields");

        ButtonType submitButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        // Create fields
        TextField name = new TextField();
        name.setPromptText("Enter new Playlist Name...");
        TextField creator = new TextField();
        creator.setPromptText("Enter creator Name...");

        VBox content = new VBox(10, name, creator);
        dialog.getDialogPane().setContent(content);

        // Retrieve inputs when "Create" is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                Map<String, String> inputs = new HashMap<>();
                inputs.put("name", name.getText());
                inputs.put("creator", creator.getText());
                return inputs;
            }
            return null;
        });

        Optional<Map<String, String>> result = dialog.showAndWait();
        if (result.isPresent()) {
            this.manager = new PlaylistManager(result.get().get("name"), result.get().get("creator"));
        }

        this.updateLabels();
    }

    /**
     * Shows an error prompt
     * @param message
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Could not perform the action");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an info prompt
     * @param message
     */
    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Action Completed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a Yes/No prompt for confirmation
     * @param message
     * @return
     */
    private boolean showConfirmAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(message);

        // Show the alert and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();

        // Process the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked 'Yes' or 'OK'
            return true;
        } else {
            // User clicked 'No' or closed the dialog
            return false;
        }
    }

    

    @Override
    public void start(Stage primaryStage) {
        this.manager = null;
        
        primaryStage.setTitle("Music Playlist Manager"); // main window

        // Main top vertical box to show all details
        VBox root = new VBox();
        root.setPadding(new Insets(20, 20, 20, 20)); // 10px padding on all sides
        root.setSpacing(10); // 10px padding between children
        
        // TitledPane nowPlayingPane = new TitledPane("NOW PLAYING", this.nowPlaying);
        Label headerLabel = new Label("NOW PLAYING");
        headerLabel.getStyleClass().add("header-label");
        // Use a StackPane to overlay the header label on the VBox
        StackPane headerPane = new StackPane(nowPlaying);
        headerPane.getChildren().add(headerLabel);
        StackPane.setAlignment(headerLabel, Pos.TOP_CENTER);
        headerLabel.setTranslateY(-10);

        /** Arranging the buttons in the main box */
        Label playlistBtnLbl = new Label("PLAYLIST OPTIONS");
        playlistBtnLbl.getStyleClass().add("header-label");

        // Use a StackPane to overlay the header label on the VBox
        HBox playlistOptBox = new HBox(this.createPlaylist, this.editPlaylist, this.showPlaylist, this.sortPlaylist,
                this.deletePlaylist, this.clearPlaylist, this.savePlaylist, this.loadPlaylist);
        playlistOptBox.getStyleClass().add("section-box");

        StackPane playlistButtons = new StackPane(playlistOptBox);
        playlistButtons.getChildren().add(playlistBtnLbl);
        StackPane.setAlignment(playlistBtnLbl, Pos.TOP_CENTER);
        playlistBtnLbl.setTranslateY(-10);

        Label songBtnLbl = new Label("SONG OPTIONS");
        songBtnLbl.getStyleClass().add("header-label");
        // Use a StackPane to overlay the header label on the VBox
        HBox songOptBox = new HBox(this.addSong, this.editSong,
        this.deleteSong, this.moveSong, this.previousSong, this.searchSong, this.nextSong);
        songOptBox.getStyleClass().add("section-box");

        StackPane songButtons = new StackPane(songOptBox);
        songButtons.getChildren().addAll(songBtnLbl);
        StackPane.setAlignment(songBtnLbl, Pos.TOP_CENTER);
        songBtnLbl.setTranslateY(-10);

        root.getChildren().addAll(playlistInfo, headerPane, playlistButtons, songButtons);
        root.getStyleClass().add("main-box");

        this.scene = new Scene(root, 600, 600);
        this.scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        
        launch(args);
    }
}
