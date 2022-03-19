package net.parksy.j8;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isEmpty;

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
     * @param file
     */
    public static void readDataLineByLine(String file)
    {

        try {
            List<String> cols;
            List<List> rows = new ArrayList<>();
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                cols = new ArrayList<>();
                    for (String cell : nextRecord) {
                        cols.add(cell);
                    }
                    rows.add(cols);
            }

            // Now Print what we've
            for ( List row : rows) {
                for ( Object col : row ) {
                    System.out.print( col + ", ");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
