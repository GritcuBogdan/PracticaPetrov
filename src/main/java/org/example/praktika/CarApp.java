package org.example.praktika;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CarApp extends Application {



    @FXML
    private Label totalPriceLabel;

    public Label getTotalPriceLabel() {
        return totalPriceLabel;
    }

    private static CarApp startController;

    public static CarApp getStartController() {
        return startController;
    }

    public void updateTotalPrice(double amount) {
        double currentTotal = Double.parseDouble(totalPriceLabel.getText());
        double newTotal = currentTotal + amount;
        totalPriceLabel.setText(String.valueOf(newTotal));
    }

    public void updateTotalPrice2(double amount) {
        double currentTotal = Double.parseDouble(totalPriceLabel.getText());
        double newTotal = currentTotal - amount;
        totalPriceLabel.setText(String.valueOf(newTotal));
    }

    public void setTotalPriceLabel(Label totalPriceLabel) {
        this.totalPriceLabel = totalPriceLabel;
    }

    private  Stage primaryStage;

    public  void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button addCarButton;



    @FXML
    private void handleAddCarButtonAction() {
        try {
            // Load AddCar.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCar.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Adaugă o mașină");
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCarRentButtonAction() {
        try {
            // Load AddCar.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CarRent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Inchiriaz o mașină");
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddFilialaButtonAction() {
        try {
            // Load AddCar.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFiliala.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Adaugă o filiala");
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowFilialaButtonAction() {
        try {
            // Load AddCar.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowFiliala.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Filiale");
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowCarsButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowCar.fxml"));
            Parent root = loader.load();

            ShowCarController controller = loader.getController();
            controller.setPrimaryStage(primaryStage); // Pass primaryStage to controller

            Stage stage = new Stage();
            stage.setTitle("Afișare mașini");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setPrimaryStage(primaryStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Start.fxml"));
        Parent root = loader.load();
        startController = loader.getController(); // Assign the controller instance

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Car Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

}
