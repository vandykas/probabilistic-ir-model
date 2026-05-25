import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BIM {
    private final Map<String, Map<Integer, Integer>> invertedIndex;
    private final Map<String, Integer> documentFrequency;
    private final int documentCount;

    public BIM(Map<String, Map<Integer, Integer>> invertedIndex, Map<String, Integer> documentFrequency,  int documentCount) {
        this.invertedIndex = invertedIndex;
        this.documentFrequency = documentFrequency;
        this.documentCount = documentCount;
    }

    public List<SearchResult> rankDocuments(ArrayList<String> queryTerm) {
        Map<Integer, Double> documentsScore = calculateDocumentsRSV(queryTerm);
        List<SearchResult> documentRanking = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : documentsScore.entrySet()) {
            documentRanking.add(new SearchResult(entry.getKey(), entry.getValue()));
        }
        Collections.sort(documentRanking);
        return documentRanking;
    }

    private Map<Integer, Double> calculateDocumentsRSV(ArrayList<String> queryTerm) {
        Map<Integer, Double> documentsScore = new HashMap<>();
        for (String term : queryTerm) {
            if (!invertedIndex.containsKey(term)) {
                continue;
            }

            for (Map.Entry<Integer, Integer> entry : invertedIndex.get(term).entrySet()) {
                int docId = entry.getKey();
                int nt = documentFrequency.get(term);
                documentsScore.put(docId, documentsScore.getOrDefault(docId, 0.0) + calculateWeight(nt));
            }
        }
        return documentsScore;
    }

    private double calculateWeight(int nt) {
        return Math.log10(0.5 * documentCount / nt);
    }
}
