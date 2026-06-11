import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ProbabilisticModel {
    protected final InvertedIndex invertedIndex;

    public ProbabilisticModel(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public abstract List<SearchResult> rankDocuments(ArrayList<String> queryTerm);

    protected void sortDocumentsRank(List<SearchResult> results) {
        Collections.sort(results);
    }
}
