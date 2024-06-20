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

public class CarController implements Controller {


    @FXML
    private Label warningLabel;
    @Override
    public int getMaxTextFieldLength(TextField textField) {
        if(textField == inmatriculareTF){
            return 7;
        }
        else if (textField == fabricatieTF){
            return 4;
        }
        else
            return 40;
    }


    @FXML
    private void initialize(){
        producatorTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        modelTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        motorTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        fabricatieTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        pasageriTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        combustibilTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        inmatriculareTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);
        pretTF.addEventFilter(KeyEvent.KEY_TYPED, this::limitTextFieldLength);

        forceDigitsOnly(pasageriTF);
        forceDigitsOnly(fabricatieTF);
        forceDigitsOnly(pretTF);
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


    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private ImageView homeButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label producatorLabel;

    @FXML
    private TextField producatorTF;

    @FXML
    private Label modelLabel;

    @FXML
    private TextField modelTF;

    @FXML
    private Label fabricatieLabel;

    @FXML
    private TextField fabricatieTF;

    @FXML
    private Label motorLabel;

    @FXML
    private TextField motorTF;

    @FXML
    private Label combustibilLabel;

    @FXML
    private TextField combustibilTF;

    @FXML
    private Label pasageriLabel;

    @FXML
    private TextField pasageriTF;

    @FXML
    private Label inmatriculareLabel;

    @FXML
    private TextField inmatriculareTF;

    @FXML
    private Label pretLabel;

    @FXML
    private TextField pretTF;

    @FXML
    private Button submitButton;

    @FXML
    private void handleHomeButtonClicked() {
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
        String producator = producatorTF.getText();
        String model = modelTF.getText();
        String fabricatie = fabricatieTF.getText();
        String motor = motorTF.getText();
        String combustibil = combustibilTF.getText();
        String pasageri = pasageriTF.getText();
        String inmatriculare = inmatriculareTF.getText();
        String pret = pretTF.getText();

        // Check if any field is empty
        if (producator.isEmpty() || model.isEmpty() || fabricatie.isEmpty() || motor.isEmpty()
                || combustibil.isEmpty() || pasageri.isEmpty() || inmatriculare.isEmpty() || pret.isEmpty()) {
            warningLabel.setText("Completati toate campurile!");
            return;
        }

        // Clear text fields
        producatorTF.clear();
        modelTF.clear();
        fabricatieTF.clear();
        motorTF.clear();
        combustibilTF.clear();
        pasageriTF.clear();
        inmatriculareTF.clear();
        pretTF.clear();

        // Write data to file
        writeDataToFile(producator, model, fabricatie, motor, combustibil, pasageri, inmatriculare, pret);

        // Clear warning label
        warningLabel.setText("");
    }
    private void writeDataToFile(String producator, String model, String fabricatie, String motor,
                                 String combustibil, String pasageri, String inmatriculare, String pret) {
        String filename = "masini.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            writer.write("Producator: " + producator + "\n");
            writer.write("Model: " + model + "\n");
            writer.write("An fabricatie: " + fabricatie + "\n");
            writer.write("Motor: " + motor + "\n");
            writer.write("Combustibil: " + combustibil + "\n");
            writer.write("Numar pasageri: " + pasageri + "\n");
            writer.write("Nr. inmatriculare: " + inmatriculare + "\n");
            writer.write("Pret: " + pret + "\n");
            writer.write("Data: " + timestamp + "\n");
            writer.write("----------------------------------------\n");
            writer.newLine();

            System.out.println("Data written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
