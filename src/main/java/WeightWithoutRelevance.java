public class WeightWithoutRelevance implements WeightCalculator {
    private final Corpus corpus;

    public WeightWithoutRelevance(Corpus corpus) {
        this.corpus = corpus;
    }

    @Override
    public double calculateWeight(String term) {
        int n = corpus.getDocumentCount();
        int nt =  corpus.getDocumentsFrequency().get(term);
        return Math.log10(0.5 * n / nt);
    }
}
