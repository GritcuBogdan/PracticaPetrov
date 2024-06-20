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

public class ShowFilialaController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Filiala> tableView;

    @FXML
    private TableColumn<Filiala, String> numeCol;

    @FXML
    private TableColumn<Filiala, String> adresaCol;

    @FXML
    private TableColumn<Filiala, String> telefonCol;

    @FXML
    private TableColumn<Filiala, String> contactCol;

    @FXML
    private Label accesLabel;

    @FXML
    private Label inmatriculareLabel;

    @FXML
    private Label telefonLabel;

    @FXML
    private TextField inmatriculareTF;

    @FXML
    private Button submitButton;

    @FXML
    private Button adaugaImageView;

    private String filename = "filiale.txt";

    public void initialize() {
        // Initialize TableView columns
        initTableColumns();

        // Load data into TableView
        loadDataFromFile();

        // Hide access label, adauga label, text field, and submit button initially
        accesLabel.setVisible(false);
        inmatriculareTF.setVisible(false);
        submitButton.setVisible(false);

        // Disable editing in TableView initially
        tableView.setEditable(false);
    }

    private void initTableColumns() {
        numeCol.setCellValueFactory(new PropertyValueFactory<>("nume"));
        adresaCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        telefonCol.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("persoanaContact"));

        // Make columns editable
        numeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        adresaCol.setCellFactory(TextFieldTableCell.forTableColumn());
        telefonCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Handle edit commit events
        numeCol.setOnEditCommit(this::handleEditCommit);
        adresaCol.setOnEditCommit(this::handleEditCommit);
        telefonCol.setOnEditCommit(this::handleEditCommit);
        contactCol.setOnEditCommit(this::handleEditCommit);
    }

    private void handleEditCommit(TableColumn.CellEditEvent<Filiala, String> event) {
        Filiala filiala = event.getRowValue();
        if (event.getTableColumn() == numeCol) {
            filiala.setNume(event.getNewValue());
        } else if (event.getTableColumn() == adresaCol) {
            filiala.setAdresa(event.getNewValue());
        } else if (event.getTableColumn() == telefonCol) {
            filiala.setTelefon(event.getNewValue());
        } else if (event.getTableColumn() == contactCol) {
            filiala.setPersoanaContact(event.getNewValue());
        }
        saveDataToFile();
    }

    @FXML
    private void handleUpdateButtonClicked() {
        accesLabel.setVisible(true);
        tableView.setEditable(true);

    }


    private void loadDataFromFile() {
        ObservableList<Filiala> filiale = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> filialaData = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Nume:")) {
                    filialaData.add(line.substring("Nume: ".length()));
                } else if (line.startsWith("Adresa:")) {
                    filialaData.add(line.substring("Adresa: ".length()));
                } else if (line.startsWith("Telefon:")) {
                    filialaData.add(line.substring("Telefon: ".length()));
                } else if (line.startsWith("Persoana de Contact:")) {
                    filialaData.add(line.substring("Persoana de Contact: ".length()));
                    filiale.add(new Filiala(filialaData.get(0), filialaData.get(1), filialaData.get(2), filialaData.get(3)));
                    filialaData.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView.setItems(filiale);
    }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Filiala filiala : tableView.getItems()) {
                writer.write("Nume: " + filiala.getNume() + "\n");
                writer.write("Adresa: " + filiala.getAdresa() + "\n");
                writer.write("Telefon: " + filiala.getTelefon() + "\n");
                writer.write("Persoana de Contact: " + filiala.getPersoanaContact() + "\n");
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
    private void handleDeleteImageViewClicked() {
        inmatriculareLabel.setVisible(true);
        inmatriculareTF.setVisible(true);
        submitButton.setVisible(true);
    }

    @FXML
    private void handleSubmitButtonClicked() {
        String nume = inmatriculareTF.getText().trim();
        ObservableList<Filiala> currentFiliale = tableView.getItems();
        ObservableList<Filiala> updatedFiliale = FXCollections.observableArrayList();

        boolean found = false;

        for (Filiala filiala : currentFiliale) {
            if (filiala.getNume().equalsIgnoreCase(nume)) {
                found = true;
            } else {
                updatedFiliale.add(filiala);
            }
        }

        if (found) {
            tableView.setItems(updatedFiliale);
            saveDataToFile();
        } else {
            // Handle case where entry was not found
            System.out.println("Entry with name '" + nume + "' not found.");
        }

        inmatriculareTF.clear();
        inmatriculareLabel.setVisible(false);
        inmatriculareTF.setVisible(false);
        submitButton.setVisible(false);
    }


    @FXML
    private void handleAddImageViewClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFiliala.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Adaugă o filiala");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleDeleteSubmitButtonClicked() {
        String numeToDelete = inmatriculareTF.getText().trim();
        ObservableList<Filiala> currentFiliale = tableView.getItems();
        ObservableList<Filiala> updatedFiliale = FXCollections.observableArrayList();

        for (Filiala filiala : currentFiliale) {
            if (!filiala.getNume().equalsIgnoreCase(numeToDelete)) {
                updatedFiliale.add(filiala);
            }
        }

        tableView.setItems(updatedFiliale);
        saveDataToFile();

        inmatriculareTF.clear();
        inmatriculareLabel.setVisible(false);
        inmatriculareTF.setVisible(false);
        submitButton.setVisible(false);
    }
}
