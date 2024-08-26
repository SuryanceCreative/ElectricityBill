package com.example.firstproject;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
//import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ElectricityConsumptionCalculatorFX extends Application {
    private final List<String> applianceNames = new ArrayList<>();
    private final List<Double> powerRatings = new ArrayList<>();
    private final List<Integer> numberOfAppliances = new ArrayList<>();
    private final List<Double> usageTimes = new ArrayList<>();
    private final List<Double> electricityRates = new ArrayList<>();
    private final List<Integer> numberOfDays = new ArrayList<>();

    private final Label totalCostLabel = new Label();
    private final List<Label> consumptionLabels = new ArrayList<>();
    private final List<Label> costLabels = new ArrayList<>();

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
        GridPane.setConstraints(titleLabel, 0, 0, 4, 1);

        initializeAppliances();

        for (int i = 0; i < applianceNames.size(); i++) {
            addUIComponents(grid, i);
        }

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateTotalBill());
        GridPane.setConstraints(calculateButton, 0, applianceNames.size() + 2);

        totalCostLabel.setStyle("-fx-font-size: 14pt;");
        GridPane.setConstraints(totalCostLabel, 0, applianceNames.size() + 3, 4, 1);

        VBox contentLayout = new VBox(20); // Vertical box with 20-pixel spacing
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.getChildren().addAll(titleLabel, grid, calculateButton, totalCostLabel);

        StackPane root = new StackPane(contentLayout);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeAppliances() {
        String[] predefinedAppliances = {
                "Ceiling Fan", "Incandescent Bulb", "CFL Bulb", "LED Bulb", "Refrigerator",
                "Television", "Air Conditioner", "Washing Machine", "Microwave Oven", "Electric Induction",
                "Laptop", "Desktop Computer", "Router", "Water Heater"
        };

        double[] predefinedPowerRatings = {
                0.075, 0.1, 0.015, 0.005, 0.15, 0.1, 1.5, 0.8, 1.2, 2.0,
                0.05, 0.1, 0.02, 2.0
        }; // Power ratings in kW

        double[] predefinedElectricityRates = {
                5.0, 3.0, 4.0, 2.0, 7.0, 4.0, 8.0, 7.0, 5.0, 6.0,
                2.0, 4.0, 1.0, 7.0
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
        numAppliancesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numAppliancesField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        numAppliancesField.setPromptText("Number of Appliance");
        GridPane.setConstraints(numAppliancesField, 1, index + 1);


        TextField usageTimeField = new TextField();
        usageTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove non-digit characters
            String cleanedValue = newValue.replaceAll("[^\\d]", "");

            // Limit the value to 24
            int intValue = cleanedValue.isEmpty() ? 0 : Integer.parseInt(cleanedValue);
            if (intValue > 24) {
                usageTimeField.setText("24");
            } else {
                usageTimeField.setText(cleanedValue);
            }
        });
        usageTimeField.setPromptText("Usage per day (hours)");
        GridPane.setConstraints(usageTimeField, 2, index + 1);



//        usageTimeField.setPromptText("Usage per day (hours)");
//        GridPane.setConstraints(usageTimeField, 2, index + 1);

        TextField numDaysField = new TextField();
        numDaysField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numDaysField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        numDaysField.setPromptText("Number of Days");
        GridPane.setConstraints(numDaysField, 3, index + 1);

        Label consumptionLabel = new Label();
        consumptionLabels.add(consumptionLabel);
        GridPane.setConstraints(consumptionLabel, 4, index + 1);

        Label costLabel = new Label();
        costLabels.add(costLabel);
        GridPane.setConstraints(costLabel, 5, index + 1);

        final String applianceName = applianceNames.get(index);
        grid.getChildren().addAll(nameLabel, numAppliancesField, usageTimeField, numDaysField, consumptionLabel, costLabel);

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

            // Update the labels for each appliance
            consumptionLabels.get(i).setText("Consumption: " + electricityConsumption + " kWh");
            costLabels.get(i).setText("Cost: Rs. " + electricityCost);
        }

        totalCostLabel.setText("Total Electricity Bill for all appliances: Rs. " + totalCost);
    }

    private double calculateElectricityConsumption(double powerRating, int numAppliances, double usageTime, int numDays) {
        return powerRating * numAppliances * usageTime * numDays;
    }

    private double calculateElectricityCost(double electricityConsumption, double electricityRate) {
        return electricityConsumption * electricityRate;
    }
}







