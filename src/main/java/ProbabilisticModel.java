import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ProbabilisticModel {
    protected final InvertedIndex invertedIndex;
    protected final Corpus corpus;

    public ProbabilisticModel(InvertedIndex invertedIndex,  Corpus corpus) {
        this.invertedIndex = invertedIndex;
        this.corpus = corpus;
    }

    public abstract List<SearchResult> rankDocuments(ArrayList<String> queryTerm);

    protected void sortDocumentsRank(List<SearchResult> results) {
        Collections.sort(results);
    }
}
