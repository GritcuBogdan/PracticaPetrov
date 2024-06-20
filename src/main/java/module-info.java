module org.example.praktika {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.praktika to javafx.fxml;
    exports org.example.praktika;
}