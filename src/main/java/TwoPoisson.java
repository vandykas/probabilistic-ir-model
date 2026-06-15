public class TwoPoisson extends ProbabilisticModel {
    private final WeightCalculator weightCalculator;

    private final double k = 1.5;

    public TwoPoisson(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
        super(invertedIndex, corpus);
        this.weightCalculator = weightCalculator;
    }

    @Override
    protected double calculateScore(String term, int tf, int docId) {
        double wt = weightCalculator.calculateWeight(term);

        double numerator = tf * (k + 1) * wt;
        double denominator = tf + k;

        return numerator / denominator;
    }
}