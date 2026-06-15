
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BM11 extends ProbabilisticModel {

    private final WeightCalculator weightCalculator;

    private final double k = 1.5;

    public BM11(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
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

                double score = calculateBM11Score(term, tf, docId);
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

    private double calculateBM11Score(String term, int tf, int docId) {
        double wt = weightCalculator.calculateWeight(term);

        double ld = corpus.getDocumentsLength()[docId];
        double avgdl = corpus.getDocumentsAvgLength();

        double numerator = tf * (k + 1) * wt;
        double denominator = tf + k * (ld / avgdl);

        return numerator / denominator;
    }
}
