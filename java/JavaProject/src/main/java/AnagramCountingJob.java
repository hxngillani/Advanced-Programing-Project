/*
HASSAN SHABIR
MASTER OF COMPUTER SCIENCE AND NETWROKING
2021-2022
*/


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnagramCountingJob extends AJob {

    private final String filename; // Name of the file in which read the words
    
    public AnagramCountingJob(String filename) {
        this.filename = filename;
    }
    
    @Override
    public Stream<Pair<String, String>> execute() {
        try {
            // Read the lines from the file and split them into individual words
            // Filter out words that are not allowed or that contain non-alphabetic characters
            // Map each word to a pair of its ciao string and the original word
            return Files.lines(Paths.get(filename))
                .flatMap(line -> Stream.of(line.split("\\s+")))
                .filter(word -> word.length() >= 4)
                .filter(word -> !word.chars().anyMatch(c -> !Character.isLetter(c)))
                .map(word -> new Pair(
                    Stream.of(word.split(""))
                        .sorted()
                        .collect(Collectors.joining("")),
                    word
                ));
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
            return Stream.empty();
        }
    }
}
