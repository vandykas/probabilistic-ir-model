import java.util.*;

public class BIM {
    private final Map<String, Map<Integer, Integer>> invertedIndex;
    private final int documentCount;

    public BIM(Map<String, Map<Integer, Integer>> invertedIndex,  int documentCount) {
        this.invertedIndex = invertedIndex;
        this.documentCount = documentCount;
    }

    public List<SearchResult> rankDocuments(String[] queryTerm) {
        Map<Integer, Double> documentsScore = calculateDocumentsRSV(queryTerm);
        List<SearchResult> documentRanking = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : documentsScore.entrySet()) {
            documentRanking.add(new SearchResult(entry.getKey(), entry.getValue()));
        }
        Collections.sort(documentRanking);
        return documentRanking;
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
