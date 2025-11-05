module com.example.island {
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
    //requires com.example.island;
    //requires com.example.island;

    opens com.example.island to javafx.fxml;
    exports com.example.island;
    exports com.example.island.location;
    opens com.example.island.location to javafx.fxml;
    exports com.example.island.animals;
    opens com.example.island.animals to javafx.fxml;
}