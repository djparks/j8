package parksy.net.j8;

import java.io.File;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "j8", header = "%n@|green Sample demo|@")
public class J8Application implements Runnable {

    @Option(names = {"-f", "--file"}, required = true, description = "Filename")
    private File file;

    @Override
    public void run() {
        System.out.printf("Loading %s%n", file.getAbsolutePath());
    }

    public static void main(String[] args) {
        CommandLine.run(new J8Application(), System.err, args);
    }
}
