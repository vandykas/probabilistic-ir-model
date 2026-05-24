import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TextPreprocess {
    private final Stemmer stemmer;
    private final Set<String> stopWords;
    private static TextPreprocess instantiation;

    private TextPreprocess() {
        this.stemmer = new Stemmer();
        this.stopWords = readStopWordsList();
    }

    public static TextPreprocess getInstantiation() {
        if (instantiation == null) {
            instantiation = new TextPreprocess();
        }
        return instantiation;
    }

    public Set<String> readStopWordsList() {
        Set<String> stopWordsList = new HashSet<>();
        Path document = Path.of("src/main/resources/stopwords.txt");
        if (Files.exists(document)) {
            try (BufferedReader reader = Files.newBufferedReader(document)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isBlank()) {
                        stopWordsList.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        return stopWordsList;

    }

    public ArrayList<String> preprocess(String text) {
        String[] tokensWithStopword = tokenize(text);
        ArrayList<String> tokensWithoutStopword = new ArrayList<>();
        for (String token : tokensWithStopword) {
            if (stopWords.contains(token)) {
                continue;
            }
            tokensWithoutStopword.add(token);
        }

        tokensWithoutStopword.replaceAll(stemmer::stem);
        return tokensWithoutStopword;
    }

    private String[] tokenize(String text) {
        text = text.toLowerCase();
        return text.split("[^a-z0-9']+");
    }
}
