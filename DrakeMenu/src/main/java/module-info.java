module com.example.drakemenu {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens thedrake.ui to javafx.fxml;
    opens thedrake to javafx.fxml;
    exports thedrake;
    exports thedrake.ui to javafx.fxml;
}