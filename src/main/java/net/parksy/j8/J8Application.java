package net.parksy.j8;

import java.io.File;
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
        ReadCSV.readDataLineByLine(csv.getAbsolutePath());
        System.out.println("_______________________________________________");
    }

    public static void main(String[] args) {
        CommandLine.run(new J8Application(), System.err, args);
    }
}
