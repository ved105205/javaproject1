package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class StatisticalAnalysis {

    private static List<Double> fetchData(String csvFile, String columnName) {
        List<Double> data = new ArrayList<>();
        try (Reader reader = new FileReader(csvFile);
             CSVParser csv = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csv) {
                String valueStr = record.get(columnName);
                // Remove commas from the string if present
                valueStr = valueStr.replaceAll(",", "");
                double value = Double.parseDouble(valueStr);
                data.add(value);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data: " + e.getMessage());
        }
        return data;
    }

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\hp\\Desktop\\Java project\\iplanlysis\\src\\main\\resources\\iploriginal.csv"; // Path to your CSV file
        Scanner scanner = new Scanner(System.in);

        try {
            boolean exit = false;
            while (!exit) {
                System.out.println("**********");
                System.out.println("1. Mean");
                System.out.println("------------------------------");
                System.out.println("2. Median");
                System.out.println("------------------------------");
                System.out.println("3. Mode");
                System.out.println("------------------------------");
                System.out.println("4. Standard Deviation");
                System.out.println("------------------------------");
                System.out.println("5. Range");
                System.out.println("------------------------------");
                System.out.println("6. Interquartile Range");
                System.out.println("------------------------------");
                System.out.println("7. Exit");

                System.out.print("Enter your choice:\n ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        calculateMean(fetchData(csvFile, "first_ings_score"));
                        break;
                    case 2:
                        calculateMedian(fetchData(csvFile, "first_ings_score"));
                        break;
                    case 3:
                        calculateMode(fetchData(csvFile, "first_ings_score"));
                        break;
                    case 4:
                        calculateStandardDeviation(fetchData(csvFile, "first_ings_score"));
                        break;
                    case 5:
                        calculateRange(fetchData(csvFile, "first_ings_score"));
                        break;
                    case 6:
                        calculateInterquartileRange(fetchData(csvFile, "first_ings_score"));
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 7.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static Double calculateMean(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for calculation.");
            return null;
        }
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        double mean = sum / data.size();
        System.out.println("Mean: " + mean);
        return mean;
    }

    public static Double calculateMedian(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for calculation.");
            return null;
        }
        // Sort the list
        Collections.sort(data);
        double median;
        int size = data.size();
        if (size % 2 == 0) {
            median = (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            median = data.get(size / 2);
        }
        System.out.println("Median: " + median);
        return median;
    }

    public static Double calculateMode(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for calculation.");
            return null;
        }
        
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : data) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        double mode = 0;
        int maxFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mode = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        System.out.println("Mode: " + mode);
        return mode;
    }

    public static Double calculateStandardDeviation(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for calculation.");
            return null;
        }
        
        double sum = 0;
        double mean = calculateMean(data);
        for (double value : data) {
            sum += Math.pow(value - mean, 2);
        }
        double variance = sum / data.size();
        double standardDeviation = Math.sqrt(variance);
        System.out.println("Standard Deviation: " + standardDeviation);
        return standardDeviation;
    }

    public static void calculateRange(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for calculation.");
            return;
        }
        
        double min = Collections.min(data);
        double max = Collections.max(data);
        double range = max - min;
        System.out.println("Range: " + range);
    }

    public static void calculateInterquartileRange(List<Double> data) {
        if (data.isEmpty()) {
            System.out.println("No data available for calculation.");
            return;
        }
        
        Collections.sort(data);
        int size = data.size();
        double lowerQuartile, upperQuartile;
        if (size % 2 == 0) {
            lowerQuartile = calculateMedian(data.subList(0, size / 2));
            upperQuartile = calculateMedian(data.subList(size / 2, size));
        } else {
            lowerQuartile = calculateMedian(data.subList(0, size / 2));
            upperQuartile = calculateMedian(data.subList(size / 2 + 1, size));
        }
        double interquartileRange = upperQuartile - lowerQuartile;
        System.out.println("Interquartile Range: " + interquartileRange);
    }
}
