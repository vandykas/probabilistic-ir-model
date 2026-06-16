public class BM11 extends ProbabilisticModel {

    private final WeightCalculator weightCalculator;

    private final double k = 1.5;

    public BM11(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
        super(invertedIndex, corpus);
        this.weightCalculator = weightCalculator;
    }

    @Override
    protected double calculateScore(String term, int tf, int docId) {
        double wt = weightCalculator.calculateWeight(term);

        double ld = corpus.getDocumentsLength()[docId];
        double avgdl = corpus.getDocumentsAvgLength();

        double numerator = tf * (k + 1) * wt;
        double denominator = tf + k * (ld / avgdl);

        return numerator / denominator;
    }
}
