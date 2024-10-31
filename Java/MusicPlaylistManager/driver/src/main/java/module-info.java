module com.dsad.music.driver {
    requires com.dsad.music.cli;
    requires com.dsad.music.gui;
    requires com.dsad.music.core;
    requires javafx.controls;  // JavaFX for GUI
    requires transitive javafx.graphics;
    exports com.dsad.music;
}
