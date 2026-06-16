public class BM25 extends ProbabilisticModel {
    private final WeightCalculator weightCalculator;

    private final double k = 1.5;
    private final double b = 0.75;

    public BM25(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
        super(invertedIndex, corpus);
        this.weightCalculator = weightCalculator;
    }

    @Override
    protected double calculateScore(String term, int tf, int docId) {
        double wt = weightCalculator.calculateWeight(term);

        double ld = corpus.getDocumentsLength()[docId];
        double avgdl = corpus.getDocumentsAvgLength();

        double numerator = tf * (k + 1) * wt;
        double denominator = tf + k * (ld / avgdl) * b + k * (1 - b);

        return numerator / denominator;
    }
}