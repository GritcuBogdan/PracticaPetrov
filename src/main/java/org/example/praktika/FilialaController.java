package org.example.praktika;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

public class FilialaController implements Controller{
    @Override
    public int getMaxTextFieldLength(TextField textField) {
        if(textField == telefonTF){
            return 9;
        }
        else
            return 40;
    }


    @Override
    public void forceDigitsOnly(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> formatter = new TextFormatter<>(filter);
        textField.setTextFormatter(formatter);
    }

    @Override
    public void limitTextFieldLength(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        if (textField.getText().length() >= getMaxTextFieldLength(textField)) {
            event.consume();
        }
    }

    @FXML
    private Label warningLabel;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private ImageView homeButton;

    @FXML
    private TextField numeTF;

    @FXML
    private TextField adresaTF;

    @FXML
    private TextField telefonTF;

    @FXML
    private TextField contactTF;

    @FXML
    private Button submitButton;

    @FXML
    private Label resultLabel; // Optional: to show a result message

    @FXML
    private void initialize() {
        telefonTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        contactTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        adresaTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        numeTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);

        forceDigitsOnly(telefonTF);
    }

    public void handleHomeButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
            Parent root = loader.load();

            if (primaryStage != null) {
                primaryStage.setScene(new Scene(root));
            } else {
                System.out.println("Primary stage is null.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmitButtonAction() {
        String nume = numeTF.getText().trim();
        String adresa = adresaTF.getText().trim();
        String telefon = telefonTF.getText().trim();
        String contact = contactTF.getText().trim();

        // Check if any required field is empty
        if (nume.isEmpty() || adresa.isEmpty() || telefon.isEmpty() || contact.isEmpty()) {
            warningLabel.setText("Completați toate rândurile!");
            return; // Stop further execution
        }

        // Clear fields after submission
        numeTF.clear();
        adresaTF.clear();
        telefonTF.clear();
        contactTF.clear();

        writeDataToFile(nume, adresa, telefon, contact);
    }

    private void writeDataToFile(String nume, String adresa, String telefon, String contact) {
        String filename = "filiale.txt"; // Adjust filename as needed
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            writer.write("Nume: " + nume + "\n");
            writer.write("Adresa: " + adresa + "\n");
            writer.write("Telefon: " + telefon + "\n");
            writer.write("Persoana de Contact: " + contact + "\n");
            writer.write("Data: " + timestamp + "\n");
            writer.write("----------------------------------------\n");
            writer.newLine();

            System.out.println("Data written to " + filename);
            resultLabel.setText("Datele au fost salvate cu succes!"); // Optional: Show success message
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
