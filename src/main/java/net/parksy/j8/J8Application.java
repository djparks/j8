package net.parksy.j8;

import java.io.File;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "j8", header = "%n@|green Jenerate Code Generator|@")
public class J8Application implements Runnable {
@CommandLine.Parameters(index = "0", description = "CSV Path/Filename", paramLabel = "CSV File") File csv;
//@CommandLine.Parameters(index = "1", description = "Template Path/Filename", paramLabel = "Template File") File template;

    @Option(names = {"-f", "--file"}, description = "Filename")
    private File file;

    @Override
    public void run() {
//        System.out.printf("Loading %s%n", file.getAbsolutePath());
        System.out.printf("Loading %n");

        System.out.println("Read Data Line by Line With Header \n");
        List<List<String>> rows = ReadCSV.readDataLineByLine(csv.getAbsolutePath());
        // Now Print what we've
        if (rows == null) {
            System.out.println("No data in CSV file");
            System.exit(1);
        }
        for ( List<String> row : rows) {
            for ( String col : row ) {
                System.out.print( col + ", ");
            }
            System.out.println();
        }
        System.out.println("_______________________________________________");
    }

    public static void main(String[] args) {
        CommandLine.run(new J8Application(), System.err, args);
    }
}
