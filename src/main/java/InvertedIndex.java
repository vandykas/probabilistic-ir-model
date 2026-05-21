import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InvertedIndex {
    private final Map<String, Map<Integer, Integer>> invertedIndex;
    private final Set<String> vocab;

    public InvertedIndex(int invertedIndexSize) {
        invertedIndex = new HashMap<>();
        vocab = new HashSet<>();
        buildInvertedIndex(invertedIndexSize);
    }

    public Map<String, Map<Integer, Integer>> getInvertedIndex() {
        return invertedIndex;
    }

    public Set<String> getVocab(){
        return vocab;
    }

    private void buildInvertedIndex(int invertedIndexSize) {
        Stemmer stemmer = new Stemmer();
        for (int docId = 1; docId <= invertedIndexSize; docId++) {
            String content = readDocument(docId);

            String[] tokens = content.split("[^a-z0-9]+");
            for (int i = 0; i < tokens.length; i++) {
                vocab.add(tokens[i]);
                tokens[i] = stemmer.stem(tokens[i]);
            }

            for (String term : tokens) {
                invertedIndex.putIfAbsent(term, new HashMap<>());
                if (invertedIndex.get(term).containsKey(docId)) {
                    invertedIndex.get(term).put(docId, invertedIndex.get(term).get(docId) + 1);
                }
                else {
                    invertedIndex.get(term).put(docId, 1);
                }
            }
        }
    }

    private String readDocument(int docId) {
        Path document = Path.of("src/main/resources/" + docId + ".txt");
        StringBuilder builder = new StringBuilder();
        if (Files.exists(document)) {
            try (BufferedReader reader = Files.newBufferedReader(document)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        return builder.toString().toLowerCase();
    }

    public void print() {
        for (Map.Entry<String, Map<Integer, Integer>> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            Map<Integer, Integer> postings = entry.getValue();

            System.out.println(term + " -> ");

            for (Map.Entry<Integer, Integer> posting : postings.entrySet()) {
                int docId = posting.getKey();
                int tf = posting.getValue();

                System.out.println("{id: " + docId + ", tf: " + tf + "} ");
            }
        }
    }
}