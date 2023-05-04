/*
HASSAN SHABIR
MASTER OF COMPUTER SCIENCE AND NETWROKING
2021-2022
*/


import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class AnagramsCounter implements FrameworkStrategy<String, String> {

    private final String OUTPUT_FILE = "count_anagrams";

    // Constructor

    // Method to generate a stream of jobs
    @Override
    public Stream<AJob<String, String>> emit() {
        String path;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the path: ");
        path = in.nextLine();

        // Reads the files in the folder and returns a stream of jobs
        try {
            return
                Files.walk(Paths.get(path))
                        .filter(f -> f.getFileName().toString().endsWith(".txt"))
                        .map(f -> new AnagramCountingJob(path + "/" + f.getFileName().toString()));
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
            return Stream.empty();
        }
    }

    // Method to write the output to a CSV file
    @Override
    public void output(Stream<Pair<String, List<String>>> collectedStream) {
        try (
             Writer txtFile =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE + ".txt")))) {

            // For each pair writes its ciao string and the length of its list in TXT format in the TXT file
            collectedStream
                .forEach(pair -> {
                    String key = pair.getKey();
                    List<String> values = pair.getValue();
                    String txtString = "Ciao Key: " + key + " Number of Words:" + values.size() + " List of Values: "+ values +"\n";
                    try {
                        txtFile.write(txtString);
                    }
                    catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                });
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
