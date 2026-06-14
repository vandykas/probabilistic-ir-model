import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoPoisson extends ProbabilisticModel {
    private final WeightCalculator weightCalculator;

    private final double k = 1.5;

    public TwoPoisson(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
        super(invertedIndex, corpus);
        this.weightCalculator = weightCalculator;
    }

    @Override
    public List<SearchResult> rankDocuments(ArrayList<String> queryTerms) {
        Map<Integer, Double> documentsScore = new HashMap<>();

        for (String term : queryTerms) {
            if (!invertedIndex.isContainsTerm(term)) {
                continue;
            }

            Map<Integer, Integer> postingList = invertedIndex.getPostingList(term);

            for (Map.Entry<Integer, Integer> entry : postingList.entrySet()) {
                int docId = entry.getKey();
                int tf = entry.getValue();

                double score = calculateTwoPoissonScore(term, tf);
                documentsScore.put(docId, documentsScore.getOrDefault(docId, 0.0) + score);
            }
        }

        List<SearchResult> documentRanking = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : documentsScore.entrySet()) {
            documentRanking.add(new SearchResult(entry.getKey(), entry.getValue()));
        }

        sortDocumentsRank(documentRanking);
        return documentRanking;
    }

    private double calculateTwoPoissonScore(String term, int tf) {
        double wt = weightCalculator.calculateWeight(term);

        double numerator = tf * (k + 1) * wt;
        double denominator = tf + k;

        return numerator / denominator;
    }
}