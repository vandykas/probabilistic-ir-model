import java.util.Map;

public class WeightWithRelevance implements WeightCalculator {
    private final Corpus corpus;
    private final int relevantDocumentCount;
    private final Map<String, Integer> pseudoRelevantDocuments;

    public WeightWithRelevance(Corpus corpus,  int relevantDocumentCount,  Map<String, Integer> pseudoRelevantDocument) {
        this.corpus = corpus;
        this.relevantDocumentCount = relevantDocumentCount;
        this.pseudoRelevantDocuments = pseudoRelevantDocument;
    }

    @Override
    public double calculateWeight(String term) {
        int rt = pseudoRelevantDocuments.get(term);
        int r = relevantDocumentCount;
        int n = corpus.getDocumentCount();
        int nt = corpus.getDocumentsFrequency().get(term);

        return Math.log10(((rt + 0.5) * (n - r + 1)) / ((r + 1) * (nt - rt + 0.5)));
    }
}
