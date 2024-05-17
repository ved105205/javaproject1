package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class iplanalysis {

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\hp\\Desktop\\Java project\\iplanlysis\\src\\main\\resources\\iploriginal.csv";
        String line = "";
        String csvSplitBy = ",";
        int totalRows = 0;
        int totalColumns = 0;
        int cleanedDataCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                totalRows++; // Increment row count for each line read
                totalColumns = data.length; // Set totalColumns to the length of the data array
                
                // Process data here (example: clean data)
                // For demonstration, let's count the number of non-empty fields
                for (String value : data) {
                    if (!value.isEmpty()) {
                        cleanedDataCount++;
                    }
                }

                // Print the row data
                for (String value : data) {
                    System.out.print(value + "\t");
                }
                System.out.println(); // Move to the next line for the next row
            }
            
            // Display total rows, total columns, and cleaned data count
            System.out.println("Total Rows: " + totalRows);
            System.out.println("Total Columns: " + totalColumns);
            System.out.println("Cleaned Data Count: " + cleanedDataCount);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
