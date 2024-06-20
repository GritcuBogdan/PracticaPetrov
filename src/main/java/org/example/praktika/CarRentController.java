package org.example.praktika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CarRentController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private ImageView homeButton;

    @FXML
    private TableView<Masina> tableView;

    @FXML
    private TableColumn<Masina, String> producatorCol;

    @FXML
    private TableColumn<Masina, String> modelCol;

    @FXML
    private TableColumn<Masina, String> fabricatieCol;

    @FXML
    private TableColumn<Masina, String> motorCol;

    @FXML
    private TableColumn<Masina, String> combustibilCol;

    @FXML
    private TableColumn<Masina, String> pasageriCol;

    @FXML
    private TableColumn<Masina, String> inmatriculareCol;

    @FXML
    private TableColumn<Masina, String> pretCol;

    @FXML
    private Label inmatriculareLabel;

    @FXML
    private TextField inmatriculareTF;

    @FXML
    private Button submitButton;

    @FXML
    private Button rentButton;

    @FXML
    private Label accesLabel;

    @FXML
    private TextField textfield2;

    @FXML
    private Button submitButton2;

    @FXML
    private Button repairButton;



    @FXML
    void handleRepairButtonClicked(ActionEvent event) {

        accesLabel.setVisible(true);
        textfield2.setVisible(true);
        submitButton2.setVisible(true);
    }

    private String filename = "masini.txt";

    public void initialize() {
        // Initialize TableView columns
        initTableColumns();

        // Load data into TableView from file
        loadDataFromFile();
    }

    private void initTableColumns() {
        producatorCol.setCellValueFactory(new PropertyValueFactory<>("producator"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        fabricatieCol.setCellValueFactory(new PropertyValueFactory<>("fabricatie"));
        motorCol.setCellValueFactory(new PropertyValueFactory<>("motor"));
        combustibilCol.setCellValueFactory(new PropertyValueFactory<>("combustibil"));
        pasageriCol.setCellValueFactory(new PropertyValueFactory<>("pasageri"));
        inmatriculareCol.setCellValueFactory(new PropertyValueFactory<>("inmatriculare"));
        pretCol.setCellValueFactory(new PropertyValueFactory<>("pret"));

    }

    private void loadDataFromFile() {
        ObservableList<Masina> cars = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> carData = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Producator:")) {
                    carData.add(line.substring("Producator: ".length()));
                } else if (line.startsWith("Model:")) {
                    carData.add(line.substring("Model: ".length()));
                } else if (line.startsWith("An fabricatie:")) {
                    carData.add(line.substring("An fabricatie: ".length()));
                } else if (line.startsWith("Motor:")) {
                    carData.add(line.substring("Motor: ".length()));
                } else if (line.startsWith("Combustibil:")) {
                    carData.add(line.substring("Combustibil: ".length()));
                } else if (line.startsWith("Numar pasageri:")) {
                    carData.add(line.substring("Numar pasageri: ".length()));
                } else if (line.startsWith("Nr. inmatriculare:")) {
                    carData.add(line.substring("Nr. inmatriculare: ".length()));
                } else if (line.startsWith("Pret:")) {
                    carData.add(line.substring("Pret: ".length()));
                    cars.add(new Masina(carData.get(0), carData.get(1), carData.get(2), carData.get(3),
                            carData.get(4), carData.get(5), carData.get(6), carData.get(7)));
                    carData.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView.setItems(cars);
    }

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
    private void handleDeleteSubmitButtonClicked() {
        String inmatriculareToDelete = inmatriculareTF.getText().trim();
        ObservableList<Masina> currentMasini = tableView.getItems();
        ObservableList<Masina> updatedMasini = FXCollections.observableArrayList();
        String pretMasina = "";

        for (Masina masina : currentMasini) {
            if (masina.getInmatriculare().equals(inmatriculareToDelete)) {
                pretMasina = masina.getPret();
            } else {
                updatedMasini.add(masina);
            }
        }

        if (!pretMasina.isEmpty()) {
            tableView.setItems(updatedMasini);
            saveDataToFile(updatedMasini);
            updateTotalPrice(Double.parseDouble(pretMasina));
        }

        inmatriculareTF.clear();
        inmatriculareLabel.setVisible(false);
        inmatriculareTF.setVisible(false);
        submitButton.setVisible(false);
    }


    @FXML
    private void handleDeleteSubmitButtonClicked2() {
        String inmatriculareToDelete = textfield2.getText().trim();
        ObservableList<Masina> currentMasini = tableView.getItems();
        ObservableList<Masina> updatedMasini = FXCollections.observableArrayList();
        String pretMasina = "";

        // Iterate over currentMasini to find the car to delete and retrieve its pretMasina
        for (Masina masina : currentMasini) {
            if (masina.getInmatriculare().equals(inmatriculareToDelete)) {
                pretMasina = masina.getPret();
            } else {
                updatedMasini.add(masina); // Add all other cars to updatedMasini
            }
        }

        if (!pretMasina.isEmpty()) {
            tableView.setItems(updatedMasini); // Update table view without the deleted car
            saveDataToFile(updatedMasini); // Update the masini.txt file without the deleted car
            updateTotalPrice2(Double.parseDouble(pretMasina)); // Decrease total price
        }

        textfield2.clear(); // Clear the text field after operation
        accesLabel.setVisible(false); // Hide relevant UI elements
        textfield2.setVisible(false);
        submitButton2.setVisible(false);
    }




    private void saveDataToFile(ObservableList<Masina> masini) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Masina masina : masini) {
                writer.write("Producator: " + masina.getProducator() + "\n");
                writer.write("Model: " + masina.getModel() + "\n");
                writer.write("An fabricatie: " + masina.getFabricatie() + "\n");
                writer.write("Motor: " + masina.getMotor() + "\n");
                writer.write("Combustibil: " + masina.getCombustibil() + "\n");
                writer.write("Numar pasageri: " + masina.getPasageri() + "\n");
                writer.write("Nr. inmatriculare: " + masina.getInmatriculare() + "\n");
                writer.write("Pret: " + masina.getPret() + "\n");
                writer.write("----------------------------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleRentButtonClicked() {
        inmatriculareLabel.setVisible(true);
        inmatriculareTF.setVisible(true);
        submitButton.setVisible(true);
    }

    @FXML
    private void handleDeleteButtonClicked() {
        inmatriculareLabel.setVisible(true);
        inmatriculareTF.setVisible(true);
        submitButton.setVisible(true);
    }



    private void saveRentedCar(String producator, String model, String inmatriculare, String pretMasina) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inchirieri.txt", true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            // Write car information to file
            writer.write(timestamp + " - " + producator + " " + model + " (" + inmatriculare + ") - Pret: " + pretMasina);
            writer.newLine(); // Add a new line for the next entry
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
    private void updateTotalPrice(double pretMasina) {
        CarApp.getStartController().updateTotalPrice(pretMasina);
    }

    private void updateTotalPrice2(double pretMasina) {
        CarApp.getStartController().updateTotalPrice2(pretMasina*2);
    }
}
