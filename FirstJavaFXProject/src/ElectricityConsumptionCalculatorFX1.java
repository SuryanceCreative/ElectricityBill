import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class ElectricityConsumptionCalculatorFX1 extends Application {
    // Define predefined appliance values
    private static final String[] predefinedAppliances = {
        "Ceiling Fan", "Incandescent Bulb", "CFL Bulb", "LED Bulb", "Refrigerator",
        "Television", "Air Conditioner", "Washing Machine", "Microwave Oven", "Electric Heater"
    };
    private static final double[] predefinedPowerRatings = {0.75, 0.1, 0.15, 0.05, 0.15, 0.1, 1.5, 0.8, 1.2, 2.0}; // Power ratings in kW
    private static final double[] predefinedElectricityRates = {5.0, 3.0, 4.0, 2.0, 7.0, 4.0, 8.0, 7.0, 5.0, 6.0}; // Electricity rates per kWh

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Consumption Calculator (Standard values for India)");

        VBox applianceList = new VBox(10);
        applianceList.setPadding(new Insets(20, 20, 20, 20));

        Label resultLabel = new Label("Results:");

        TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        for (int i = 0; i < predefinedAppliances.length; i++) {
            String applianceName = predefinedAppliances[i];
            Label applianceLabel = new Label(applianceName);
            TextField numTextField = new TextField();
            TextField usageTextField = new TextField();
            Button calculateButton = new Button("Calculate");

            final int index = i; // Make a final or effectively final variable

            calculateButton.setOnAction(event -> {
                int numAppliances = Integer.parseInt(numTextField.getText());
                double usageTime = Double.parseDouble(usageTextField.getText());

                double powerRating = predefinedPowerRatings[index];
                double electricityRate = predefinedElectricityRates[index];

                double electricityConsumption = powerRating * numAppliances * usageTime;
                double electricityCost = electricityConsumption * electricityRate;

                String result = "Appliance: " + applianceName + "\n" +
                        "Electricity Consumption: " + electricityConsumption + " kWh\n" +
                        "Electricity Cost: Rs. " + electricityCost;

                resultTextArea.setText(result);
            });

            VBox applianceEntry = new VBox(10);
            applianceEntry.getChildren().addAll(applianceLabel, numTextField, usageTextField, calculateButton);
            applianceList.getChildren().add(applianceEntry);
        }

        Scene scene = new Scene(new VBox(applianceList, resultLabel, resultTextArea), 400, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
