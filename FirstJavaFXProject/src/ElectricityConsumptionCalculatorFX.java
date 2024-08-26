import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ElectricityConsumptionCalculatorFX extends Application {
    private List<String> applianceNames = new ArrayList<>();
    private List<Double> powerRatings = new ArrayList<>();
    private List<Integer> numberOfAppliances = new ArrayList<>();
    private List<Double> usageTimes = new ArrayList<>();
    private List<Double> electricityRates = new ArrayList<>();
    private List<Integer> numberOfDays = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Consumption Calculator");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Label titleLabel = new Label("Welcome to the Electricity Consumption Calculator (Standard values for India)");
        titleLabel.setStyle("-fx-font-size: 16pt;");
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);

        initializeAppliances();

        for (int i = 0; i < applianceNames.size(); i++) {
            addUIComponents(grid, i);
        }

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateTotalBill());
        GridPane.setConstraints(calculateButton, 0, applianceNames.size() + 2);

        grid.getChildren().addAll(titleLabel, calculateButton);

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeAppliances() {
        String[] predefinedAppliances = {
                "Ceiling Fan", "Incandescent Bulb", "CFL Bulb", "LED Bulb", "Refrigerator",
                "Television", "Air Conditioner", "Washing Machine", "Microwave Oven", "Electric Heater",
                "Laptop", "Desktop Computer", "Router", "Water Heater", "Coffee Maker"
        };

        double[] predefinedPowerRatings = {
                0.075, 0.1, 0.015, 0.005, 0.15, 0.1, 1.5, 0.8, 1.2, 2.0,
                0.05, 0.1, 0.02, 2.0, 1.0
        }; // Power ratings in kW

        double[] predefinedElectricityRates = {
                5.0, 3.0, 4.0, 2.0, 7.0, 4.0, 8.0, 7.0, 5.0, 6.0,
                2.0, 4.0, 1.0, 7.0, 5.0
        }; // Electricity rates per kWh

        for (int i = 0; i < predefinedAppliances.length; i++) {
            applianceNames.add(predefinedAppliances[i]);
            powerRatings.add(predefinedPowerRatings[i]);
            electricityRates.add(predefinedElectricityRates[i]);
            // Initialize lists with default values
            numberOfAppliances.add(0);
            usageTimes.add(0.0);
            numberOfDays.add(0);
        }
    }

    private void addUIComponents(GridPane grid, int index) {
        Label nameLabel = new Label(applianceNames.get(index));
        GridPane.setConstraints(nameLabel, 0, index + 1);

        TextField numAppliancesField = new TextField();
        numAppliancesField.setPromptText("Number of Appliances");
        GridPane.setConstraints(numAppliancesField, 1, index + 1);

        TextField usageTimeField = new TextField();
        usageTimeField.setPromptText("Usage Time (hours)");
        GridPane.setConstraints(usageTimeField, 2, index + 1);

        TextField numDaysField = new TextField();
        numDaysField.setPromptText("Number of Days");
        GridPane.setConstraints(numDaysField, 3, index + 1);

        final String applianceName = applianceNames.get(index);  // make it final
        grid.getChildren().addAll(nameLabel, numAppliancesField, usageTimeField, numDaysField);

        numAppliancesField.setOnKeyReleased(e -> {
            try {
                numberOfAppliances.set(index, Integer.parseInt(numAppliancesField.getText()));
            } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                // Handle the exception or ignore it
            }
        });

        usageTimeField.setOnKeyReleased(e -> {
            try {
                usageTimes.set(index, Double.parseDouble(usageTimeField.getText()));
            } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                // Handle the exception or ignore it
            }
        });

        numDaysField.setOnKeyReleased(e -> {
            try {
                numberOfDays.set(index, Integer.parseInt(numDaysField.getText()));
            } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                // Handle the exception or ignore it
            }
        });
    }

    private void calculateTotalBill() {
        double totalCost = 0;

        for (int i = 0; i < applianceNames.size(); i++) {
            double powerRating = powerRatings.get(i);
            int numAppliances = numberOfAppliances.get(i);
            double usageTime = usageTimes.get(i);
            double electricityRate = electricityRates.get(i);
            int numDays = numberOfDays.get(i);

            double electricityConsumption = calculateElectricityConsumption(powerRating, numAppliances, usageTime, numDays);
            double electricityCost = calculateElectricityCost(electricityConsumption, electricityRate);

            totalCost += electricityCost;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total Electricity Bill");
        alert.setHeaderText(null);
        alert.setContentText("Total Electricity Bill for all appliances: Rs. " + totalCost);
        alert.showAndWait();
    }

    private double calculateElectricityConsumption(double powerRating, int numAppliances, double usageTime, int numDays) {
        return powerRating * numAppliances * usageTime * numDays;
    }

    private double calculateElectricityCost(double electricityConsumption, double electricityRate) {
        return electricityConsumption * electricityRate;
    }
}
