module converter {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires image4j;
    requires org.slf4j;
    requires javafx.swing;
    requires com.luciad.imageio.webp;
    requires jave.core;
    requires org.bytedeco.javacv;

    opens converter to javafx.fxml;
    exports model.converterImage;
    exports converter;
    exports model.utility;
}