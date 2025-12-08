module org.example.cs203_pz2_vukdojcinovic5907 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens org.example.cs203_pz2_vukdojcinovic5907 to javafx.fxml;
    exports org.example.cs203_pz2_vukdojcinovic5907;
}