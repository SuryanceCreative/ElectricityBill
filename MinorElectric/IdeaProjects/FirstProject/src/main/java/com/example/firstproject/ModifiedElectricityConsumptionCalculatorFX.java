package com.example.firstproject;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModifiedElectricityConsumptionCalculatorFX extends Application {
    private List<String> applianceNames = new ArrayList<>();
    private List<Double> powerRatings = new ArrayList<>();
    private List<Double> electricityRates = new ArrayList<>();

    private List<Label> applianceLabels = new ArrayList<>();
    private List<TextField> numberOfAppliancesFields = new ArrayList<>();
    private List<TextField> usageTimesFields = new ArrayList<>();
    private List<TextField> numberOfDaysFields = new ArrayList<>();

    private ComboBox<String> randomApplianceComboBox;

    private Label totalCostLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Modified Electricity Consumption Calculator");

        VBox contentLayout = new VBox(20);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.setStyle("-fx-background-color: #f4f4f4;"); // Set background color
        contentLayout.setPadding(new Insets(20, 20, 20, 20));

        initializeAppliances();

        // Create UI components for predefined appliances
        for (int i = 0; i < applianceNames.size(); i++) {
            addUIComponents(contentLayout, i);
        }

        // Add ComboBox for random appliance
        randomApplianceComboBox = createRandomApplianceComboBox();
        contentLayout.getChildren().add(randomApplianceComboBox);

        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> calculateTotalBill());
        contentLayout.getChildren().add(calculateButton);

        totalCostLabel.setStyle("-fx-font-size: 14pt;");
        contentLayout.getChildren().add(totalCostLabel);

        StackPane root = new StackPane(contentLayout);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeAppliances() {
        String[] predefinedAppliances = {
                "Ceiling Fan", "Incandescent Bulb", "CFL Bulb", "LED Bulb", "Refrigerator",
                "Television", "Air Conditioner", "Washing Machine", "Microwave Oven", "Electric Heater"
        };

        double[] predefinedPowerRatings = {
                0.075, 0.1, 0.015, 0.005, 0.15, 0.1, 1.5, 0.8, 1.2, 2.0
        };

        double[] predefinedElectricityRates = {
                5.0, 3.0, 4.0, 2.0, 7.0, 4.0, 8.0, 7.0, 5.0, 6.0
        };

        for (int i = 0; i < predefinedAppliances.length; i++) {
            applianceNames.add(predefinedAppliances[i]);
            powerRatings.add(predefinedPowerRatings[i]);
            electricityRates.add(predefinedElectricityRates[i]);
        }
    }

    private void addUIComponents(VBox contentLayout, int index) {
        Label applianceLabel = new Label(applianceNames.get(index));
        TextField numAppliancesField = new TextField();
        TextField usageTimeField = new TextField();
        TextField numDaysField = new TextField();

        applianceLabels.add(applianceLabel);
        numberOfAppliancesFields.add(numAppliancesField);
        usageTimesFields.add(usageTimeField);
        numberOfDaysFields.add(numDaysField);

        VBox applianceLayout = new VBox(10);
        applianceLayout.getChildren().addAll(applianceLabel, numAppliancesField, usageTimeField, numDaysField);

        contentLayout.getChildren().add(applianceLayout);
    }

    private ComboBox<String> createRandomApplianceComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Select Random Appliance");

        // Add predefined appliances to the dropdown
        comboBox.getItems().addAll(applianceNames);

        // Handle selection of a random appliance
        comboBox.setOnAction(e -> {
            String selectedAppliance = comboBox.getValue();
            if (selectedAppliance != null) {
                // Find the index of the selected appliance
                int index = applianceNames.indexOf(selectedAppliance);
                if (index != -1) {
                    // Set power rating and electricity rate based on the selected appliance
                    // Note: This assumes that powerRatings and electricityRates have the same order as applianceNames
                    double powerRating = powerRatings.get(index);
                    double electricityRate = electricityRates.get(index);

                    // You can display or use the powerRating and electricityRate as needed
                    System.out.println("Selected Appliance: " + selectedAppliance);
                    System.out.println("Power Rating: " + powerRating);
                    System.out.println("Electricity Rate: " + electricityRate);
                }
            }
        });

        return comboBox;
    }

    private void calculateTotalBill() {
        double totalCost = 0;

        for (int i = 0; i < applianceNames.size(); i++) {
            double powerRating = powerRatings.get(i);
            int numAppliances = getIntValue(numberOfAppliancesFields.get(i));
            double usageTime = getDoubleValue(usageTimesFields.get(i));
            double electricityRate = electricityRates.get(i);
            int numDays = getIntValue(numberOfDaysFields.get(i));

            double electricityConsumption = calculateElectricityConsumption(powerRating, numAppliances, usageTime, numDays);
            double electricityCost = calculateElectricityCost(electricityConsumption, electricityRate);

            totalCost += electricityCost;
        }

        totalCostLabel.setText(String.format("Total Cost (Rs.): %.2f", totalCost));
    }

    private double calculateElectricityConsumption(double powerRating, int numAppliances, double usageTime, int numDays) {
        return powerRating * numAppliances * usageTime * numDays;
    }

    private double calculateElectricityCost(double electricityConsumption, double electricityRate) {
        return electricityConsumption * electricityRate;
    }

    private int getIntValue(TextField textField) {
        try {
            return Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double getDoubleValue(TextField textField) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}



