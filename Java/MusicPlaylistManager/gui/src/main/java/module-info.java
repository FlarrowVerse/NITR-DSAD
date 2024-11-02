module com.dsad.music.gui {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires com.dsad.music.core;
    requires javafx.base;
    
    exports com.dsad.music.gui;

    opens com.dsad.music.gui to javafx.fxml;
}
