import java.util.HashMap;
import java.util.Map;

public class BIM {
    private final Map<String, Map<Integer, Integer>> invertedIndex;
    private final int documentCount;

    public BIM(Map<String, Map<Integer, Integer>> invertedIndex,  int documentCount) {
        this.invertedIndex = invertedIndex;
        this.documentCount = documentCount;
    }

    public Map<Integer, Double> rankDocuments(String[] queryTerm) {
        Map<Integer, Double> documentsScore = calculateDocumentsRSV(queryTerm);
        return documentsScore;
    }

    private Map<Integer, Double> calculateDocumentsRSV(String[] queryTerm) {
        Map<Integer, Double> documentsScore = new HashMap<>();
        for (String term : queryTerm) {
            for (Map.Entry<Integer, Integer> entry : invertedIndex.get(term).entrySet()) {
                int docId = entry.getKey();
                int termFrequency = entry.getValue();
                documentsScore.put(docId, documentsScore.getOrDefault(docId, 0.0) + calculateWeight(termFrequency));
            }
        }
        return documentsScore;
    }

    private double calculateWeight(int termFrequency) {
        return Math.log10(0.5 * documentCount / termFrequency);
    }
}
