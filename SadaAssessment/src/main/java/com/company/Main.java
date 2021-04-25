package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void fetch(final String dataFilePath) throws IOException, CsvException {
        //Document URL on the github
        URL url = new URL(dataFilePath);

        int totalRows=0;
        int validProfitRows = 0;
        int topNRows = 20;

        List<OrganizationData> validProfitEntries = new ArrayList<>();
        InputStreamReader reader = new InputStreamReader(url.openStream());
        CSVReader csvReader = new CSVReader(reader);
        //Skips the first line
        csvReader.skip(1);
        List<String[]> list = csvReader.readAll();

        //loop
        for(String[] row:list) {
            //Uncomment the line below to print all the rows in the list fetched from .csv file
            //System.out.println(Arrays.toString(row));

            //Keeps a count on the number of rows of data fetched from the .csv file
            totalRows++;

            //Record profits for each row
            String profit = row[(row.length) - 1];

            //if the profit is non-numeric then remove the row from the list
            if (profit.matches("-?\\d+(.\\d+)?")) {
                //counts the numeric data in the profit row
                validProfitRows++;

                OrganizationData org = new OrganizationData(
                        Integer.parseInt(row[0]), // year
                        Integer.parseInt(row[1]), // rank
                        row[2],                   // company name
                        Float.parseFloat(row[3]), // revenue
                        Float.parseFloat(row[4])  // profit
                );
                //adds it to the valid profit entries list
                validProfitEntries.add(org);
            }
        }

        //Prints the number of rows fetched from the .csv file
        System.out.println("Total number of rows of data in the CSV data: " + totalRows);
        //Prints the number of rows with numeric values left
        System.out.println("Total number of rows with only numeric values in the profit column: " + validProfitRows);

        // sort by profit
        validProfitEntries.sort(new ProfitComparator());

        //Using gson library to serialize the data
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(validProfitEntries);

        //writes to the json file called data2.json
        try (PrintStream out = new PrintStream(new FileOutputStream("data2.json"))) {
            out.print(json);
        }

        //Prints the top 20 rows with the highest profit values
        for (OrganizationData data: validProfitEntries) {
            if (topNRows > 0) {
                System.out.println(data.toString());
                topNRows--;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // write your code here
        try {
            fetch("https://gist.githubusercontent.com/bobbae/b4eec5b5cb0263e7e3e63a6806d045f2/raw/279b794a834a62dc108fc843a72c94c49361b501/data.csv");
        } catch (CsvException e) {
            System.out.println("Provided file is not a well formatted CSV");
        }
    }
}
