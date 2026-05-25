import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class InvertedIndex {
    private final Map<String, Map<Integer, Integer>> invertedIndex;
    private final Map<String, Integer> documentsFrequency;
    private final int[] documentsLength;
    private double documentsAvgLength;
    private final TextPreprocess textPreprocess;

    public InvertedIndex(int invertedIndexSize) {
        this.invertedIndex = new HashMap<>();
        this.documentsFrequency = new HashMap<>();
        this.textPreprocess = TextPreprocess.getInstantiation();
        this.documentsLength = new int[invertedIndexSize + 1];
        buildInvertedIndex(invertedIndexSize);
    }

    public Map<String, Map<Integer, Integer>> getInvertedIndex() {
        return this.invertedIndex;
    }

    public Map<String, Integer> getDocumentsFrequency() {
        return this.documentsFrequency;
    }

    private void buildInvertedIndex(int invertedIndexSize) {
        Set<String> uniqueTokens = new HashSet<>();
        ArrayList<String> tokens;
        double documentLengthTotal = 0;
        for (int docId = 1; docId <= invertedIndexSize; docId++) {
            String content = readDocument(docId);
            tokens = textPreprocess.preprocess(content);
            documentsLength[docId] = tokens.size();
            documentLengthTotal += tokens.size();

            for (String term : tokens) {
                invertedIndex.putIfAbsent(term, new HashMap<>());
                invertedIndex.get(term).put(docId, invertedIndex.get(term).getOrDefault(docId, 0) + 1);
                if (!uniqueTokens.contains(term)) {
                    documentsFrequency.put(term, documentsFrequency.getOrDefault(term, 0) + 1);
                    uniqueTokens.add(term);
                }
            }
            uniqueTokens.clear();
        }
        this.documentsAvgLength = documentLengthTotal / invertedIndexSize;
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
        return builder.toString();
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