import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ElectricityConsumptionCalculator {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> applianceNames = new ArrayList<>();
        List<Double> powerRatings = new ArrayList<>();
        List<Integer> numberOfAppliances = new ArrayList<>();
        List<Double> usageTimes = new ArrayList<>();
        List<Double> electricityRates = new ArrayList<>();
        List<Integer> numberOfDays = new ArrayList<>();

        System.out.println("Welcome to the Electricity Consumption Calculator (Standard values for India)");
        System.out.println("-----------------------------------------------------------------------");

        // Predefined list of appliances with their power ratings and electricity rates
        initializeAppliances(applianceNames, powerRatings, electricityRates);

        getInputForAppliances(applianceNames, powerRatings, numberOfAppliances, usageTimes, electricityRates, numberOfDays);

        displayElectricityInfo(applianceNames, powerRatings, numberOfAppliances, usageTimes, electricityRates, numberOfDays);

        displayTotalBill(applianceNames, powerRatings, numberOfAppliances, usageTimes, electricityRates, numberOfDays);

        System.out.println("Thank you for using the Electricity Consumption Calculator.");
    }

    private static void initializeAppliances(List<String> applianceNames, List<Double> powerRatings, List<Double> electricityRates) {
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
        }
    }

    private static void getInputForAppliances(List<String> applianceNames, List<Double> powerRatings,
            List<Integer> numberOfAppliances, List<Double> usageTimes, List<Double> electricityRates,
            List<Integer> numberOfDays) {
        for (int i = 0; i < applianceNames.size(); i++) {
            String applianceName = applianceNames.get(i);

            System.out.print("Enter the number of " + applianceName + "s (0 to skip): ");
            int numAppliances = scanner.nextInt();

            if (numAppliances == 0) {
                // Skip this appliance
                continue;
            } 

            System.out.print("Enter usage time (hours) in one day: ");
            double usageTime = scanner.nextDouble();

            System.out.print("Enter the number of days of usage: ");
            int numDays = scanner.nextInt();

            numberOfAppliances.add(numAppliances);
            usageTimes.add(usageTime);
            numberOfDays.add(numDays);
        }
    }

    private static void displayElectricityInfo(List<String> applianceNames, List<Double> powerRatings,
            List<Integer> numberOfAppliances, List<Double> usageTimes, List<Double> electricityRates,
            List<Integer> numberOfDays) {
        System.out.printf("%-20s %-15s %-15s %-20s %-20s %-20s\n",
                "Appliance", "Power Rating (kW)", "Num Appliances", "Usage Time (hours)", "Electricity Rate", "Cost (Rs.)");
        System.out.println("--------------------------------------------------------------------------------------------");

        double totalCost = 0;

        for (int i = 0; i < applianceNames.size(); i++) {
            String applianceName = applianceNames.get(i);
            double powerRating = powerRatings.get(i);
            int numAppliances = numberOfAppliances.get(i);
            double usageTime = usageTimes.get(i);
            double electricityRate = electricityRates.get(i);
            int numDays = numberOfDays.get(i);

            double electricityConsumption = calculateElectricityConsumption(powerRating, numAppliances, usageTime, numDays);
            double electricityCost = calculateElectricityCost(electricityConsumption, electricityRate);

            totalCost += electricityCost;

            System.out.printf("%-20s %-15.2f %-15d %-20.2f %-20.2f %-20.2f\n",
                    applianceName, powerRating, numAppliances, usageTime, electricityRate, electricityCost);
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-85s %.2f\n", "Total Cost (Rs.):", totalCost);
        System.out.println();
    }

    private static void displayTotalBill(List<String> applianceNames, List<Double> powerRatings,
            List<Integer> numberOfAppliances, List<Double> usageTimes, List<Double> electricityRates,
            List<Integer> numberOfDays) {
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

        System.out.println("Total Electricity Bill for all appliances: Rs. " + totalCost);
    }

    private static double calculateElectricityConsumption(double powerRating, int numAppliances, double usageTime, int numDays) {
        return powerRating * numAppliances * usageTime * numDays;
    }

    private static double calculateElectricityCost(double electricityConsumption, double electricityRate) {
        return electricityConsumption * electricityRate;
    }
}
