public class BIM extends ProbabilisticModel {
    private final WeightCalculator weightCalculator;

    public BIM(InvertedIndex invertedIndex, Corpus corpus, WeightCalculator weightCalculator) {
        super(invertedIndex, corpus);
        this.weightCalculator = weightCalculator;
    }

    @Override
    protected double calculateScore(String term, int tf, int docId) {
        return weightCalculator.calculateWeight(term);
    }
}
