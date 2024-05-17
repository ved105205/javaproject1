package com.example;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ipltest {
    

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\hp\\Desktop\\Java project\\iplanlysis\\src\\main\\resources\\iploriginal.csv";
        String line = "";
        String csvSplitBy = ",";
        DefaultPieDataset tossPieDataset = new DefaultPieDataset();
        DefaultPieDataset stagePieDataset = new DefaultPieDataset();
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length >= 14) { // Ensure there are at least 14 columns
                    try {
                        String tossWinner = data[6]; // Assuming 'toss_winner' is at index 6
                        String tossDecision = data[7]; // Assuming 'toss_decision' is at index 7
                        String matchWinner = data[12]; // Assuming 'match_winner' is at index 12
                        String margin = data[15]; // Assuming 'margin' is at index 15
                        String stage = data[5]; // Assuming 'stage' is at index 5
                        int wins = Integer.parseInt(data[13]); // Assuming 'won_by' is at index 13
                        
                        // Pie chart for toss analysis
                        tossPieDataset.setValue(tossWinner + " - " + tossDecision, wins);
                        
                        // Pie chart for stage analysis
                        stagePieDataset.setValue(stage, wins);

                        barDataset.addValue(wins, "Wins", matchWinner + " - " + margin); // Add the number of wins for the corresponding match winner and margin
                    } catch (NumberFormatException e) {
                        // Handle non-integer values in the columns
                        System.err.println("Invalid data format for row: " + line);
                    }
                } else {
                    // Handle rows with insufficient data
                    System.err.println("Incomplete data for row: " + line);
                }
            }
        } catch (IOException e) {
            // Handle file IO errors
            e.printStackTrace();
        }

        JFreeChart tossPieChart = ChartFactory.createPieChart(
                "Toss Analysis",
                tossPieDataset,
                true,
                true,
                false);

        tossPieChart.setBackgroundPaint(Color.white);

        JFreeChart stagePieChart = ChartFactory.createPieChart(
                "Stage Analysis",
                stagePieDataset,
                true,
                true,
                false);

        stagePieChart.setBackgroundPaint(Color.white);

        JFreeChart barChart = ChartFactory.createBarChart(
                "Match Analysis",
                "Match Winner - Margin",
                "Number of Wins",
                barDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        barChart.setBackgroundPaint(Color.white);

        JFrame frame = new JFrame("IPL Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Select Chart");
        JMenuItem tossPieMenuItem = new JMenuItem("Toss Pie Chart");
        JMenuItem stagePieMenuItem = new JMenuItem("Stage Pie Chart");
        JMenuItem barMenuItem = new JMenuItem("Bar Graph");
        menu.add(tossPieMenuItem);
        menu.add(stagePieMenuItem);
        menu.add(barMenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        
        // Add chart panels to the frame initially
        ChartPanel tossPieChartPanel = new ChartPanel(tossPieChart);
        tossPieChartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        frame.add(tossPieChartPanel);
        
        ChartPanel stagePieChartPanel = new ChartPanel(stagePieChart);
        stagePieChartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        frame.add(stagePieChartPanel);
        
        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        frame.add(barChartPanel);
        
        // Set layout to display charts side by side
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        tossPieMenuItem.addActionListener(e -> {
            stagePieChartPanel.setVisible(false);
            barChartPanel.setVisible(false);
            tossPieChartPanel.setVisible(true);
        });

        stagePieMenuItem.addActionListener(e -> {
            tossPieChartPanel.setVisible(false);
            barChartPanel.setVisible(false);
            stagePieChartPanel.setVisible(true);
        });

        barMenuItem.addActionListener(e -> {
            tossPieChartPanel.setVisible(false);
            stagePieChartPanel.setVisible(false);
            barChartPanel.setVisible(true);
        });

        frame.pack();
        frame.setVisible(true);
    }
}
