import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvertedIndex {
    private final Map<String, Map<Integer, Integer>> invertedIndex;
    private final Map<String, Integer> documentsFrequency;
    private final int[] documentsLength;
    private double documentsAvgLength;
    private final TextPreprocess textPreprocess;
    private final ReaderHelper reader;

    public InvertedIndex(int invertedIndexSize, ReaderHelper reader) {
        this.invertedIndex = new HashMap<>();
        this.documentsFrequency = new HashMap<>();
        this.textPreprocess = TextPreprocess.getInstantiation();
        this.documentsLength = new int[invertedIndexSize + 1];
        this.reader = reader;
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
            String content = reader.readDocument(docId);
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