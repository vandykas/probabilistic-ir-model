import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BIM extends ProbabilisticModel {
    private final WeightCalculator weightCalculator;

    public BIM(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
        super(invertedIndex, corpus);
        this.weightCalculator = weightCalculator;
    }

    @Override
    public List<SearchResult> rankDocuments(ArrayList<String> queryTerm) {
        Map<Integer, Double> documentsScore = calculateDocumentsRSV(queryTerm);
        List<SearchResult> documentRanking = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : documentsScore.entrySet()) {
            documentRanking.add(new SearchResult(entry.getKey(), entry.getValue()));
        }
        sortDocumentsRank(documentRanking);
        return documentRanking;
    }

    private Map<Integer, Double> calculateDocumentsRSV(ArrayList<String> queryTerm) {
        Map<Integer, Double> documentsScore = new HashMap<>();
        for (String term : queryTerm) {
            if (!invertedIndex.isContainsTerm(term)) {
                continue;
            }

            for (Map.Entry<Integer, Integer> entry : invertedIndex.getPostingList(term).entrySet()) {
                int docId = entry.getKey();
                documentsScore.put(
                        docId,
                        documentsScore.getOrDefault(docId, 0.0) + weightCalculator.calculateWeight(term)
                );
            }
        }
        return documentsScore;
    }
}
