package org.example.praktika;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowCarController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private ImageView homeButton;

    @FXML
    private ImageView addButton;

    @FXML
    private ImageView deleteButton;

    @FXML
    private AnchorPane rootPane;

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
    private Label accesLabel;

    @FXML
    private Label inmatriculareLabel;

    @FXML
    private TextField inmatriculareTF;

    @FXML
    private Button submitButton;

    @FXML
    private Button updateButton;

    private String filename = "masini.txt";

    public void initialize() {
        // Initialize TableView columns
        initTableColumns();

        // Load data into TableView
        loadDataFromFile();

        // Hide access label, inmatriculare label, text field, and submit button initially
        accesLabel.setVisible(false);
        inmatriculareLabel.setVisible(false);
        inmatriculareTF.setVisible(false);
        submitButton.setVisible(false);

        // Disable editing in TableView initially
        tableView.setEditable(false);
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

        // Make columns editable
        producatorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        fabricatieCol.setCellFactory(TextFieldTableCell.forTableColumn());
        motorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        combustibilCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pasageriCol.setCellFactory(TextFieldTableCell.forTableColumn());
        inmatriculareCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pretCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Handle edit commit events
        producatorCol.setOnEditCommit(this::handleEditCommit);
        modelCol.setOnEditCommit(this::handleEditCommit);
        fabricatieCol.setOnEditCommit(this::handleEditCommit);
        motorCol.setOnEditCommit(this::handleEditCommit);
        combustibilCol.setOnEditCommit(this::handleEditCommit);
        pasageriCol.setOnEditCommit(this::handleEditCommit);
        inmatriculareCol.setOnEditCommit(this::handleEditCommit);
        pretCol.setOnEditCommit(this::handleEditCommit);
    }

    private void handleEditCommit(TableColumn.CellEditEvent<Masina, String> event) {
        Masina car = event.getRowValue();
        if (event.getTableColumn() == producatorCol) {
            car.setProducator(event.getNewValue());
        } else if (event.getTableColumn() == modelCol) {
            car.setModel(event.getNewValue());
        } else if (event.getTableColumn() == fabricatieCol) {
            car.setFabricatie(event.getNewValue());
        } else if (event.getTableColumn() == motorCol) {
            car.setMotor(event.getNewValue());
        } else if (event.getTableColumn() == combustibilCol) {
            car.setCombustibil(event.getNewValue());
        } else if (event.getTableColumn() == pasageriCol) {
            car.setPasageri(event.getNewValue());
        } else if (event.getTableColumn() == inmatriculareCol) {
            car.setInmatriculare(event.getNewValue());
        } else if (event.getTableColumn() == pretCol) {
            car.setPret(event.getNewValue());
        }
        saveDataToFile();
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

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Masina car : tableView.getItems()) {
                writer.write("Producator: " + car.getProducator() + "\n");
                writer.write("Model: " + car.getModel() + "\n");
                writer.write("An fabricatie: " + car.getFabricatie() + "\n");
                writer.write("Motor: " + car.getMotor() + "\n");
                writer.write("Combustibil: " + car.getCombustibil() + "\n");
                writer.write("Numar pasageri: " + car.getPasageri() + "\n");
                writer.write("Nr. inmatriculare: " + car.getInmatriculare() + "\n");
                writer.write("Pret: " + car.getPret() + "\n");
                writer.write("----------------------------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void handleAddImageViewClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCar.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Adaugă o mașină");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateButtonClicked() {
        System.out.println("Update button clicked.");
        accesLabel.setVisible(true);
        tableView.setEditable(true);
    }

    @FXML
    private void handleDeleteImageViewClicked() {
        inmatriculareLabel.setVisible(true);
        inmatriculareTF.setVisible(true);
        submitButton.setVisible(true);
    }

    @FXML
    private void handleDeleteSubmitButtonClicked() {
        String inmatriculareToDelete = inmatriculareTF.getText().trim();
        ObservableList<Masina> currentCars = tableView.getItems();
        ObservableList<Masina> updatedCars = FXCollections.observableArrayList();

        for (Masina car : currentCars) {
            if (!car.getInmatriculare().equals(inmatriculareToDelete)) {
                updatedCars.add(car);
            }
        }

        tableView.setItems(updatedCars);
        saveDataToFile();

        inmatriculareTF.clear();
        inmatriculareLabel.setVisible(false);
        inmatriculareTF.setVisible(false);
        submitButton.setVisible(false);
    }
}