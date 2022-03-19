package net.parksy.j8;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.*;

public class ReadCSV {

    /**
     * Read CSV into Array of Map?
     * @param file
     */
    public static void readAllDataAtOnce(String file)
    {
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read a CSV file and place content into ArrayList of Map
     * @param file fileName
     * @return List of Rows with Columns
     */
    public static List<List<String>> readDataLineByLine(String file)
    {

        try {
            List<String> cols;
            List<List<String>> rows = new ArrayList<>();
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                cols = new ArrayList<>();
                Collections.addAll(cols, nextRecord);
                    rows.add(cols);
            }

            return rows;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
