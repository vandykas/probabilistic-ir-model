import java.util.ArrayList;
import java.util.List;

public class ProbabilisticRetrieval {
    private final TextPreprocess textPreprocess;
    private final BIM bimModel;

    public ProbabilisticRetrieval(InvertedIndex invertedIndex, int documentCount) {
        this.textPreprocess = TextPreprocess.getInstantiation();
        this.bimModel = new BIM(invertedIndex.getInvertedIndex(), documentCount);
    }

    public List<SearchResult> retrieveWithBIM(String query) {
        return bimModel.rankDocuments(textPreprocess.preprocess(query));
    }

    public List<SearchResult> retrieveWithBM25(String query) {
        // TODO : Membuat model BM25
        return new ArrayList<SearchResult>();
    }

    public List<SearchResult> retrieveWithBM10(String query) {
        // TODO : Membuat model BM10
        return new ArrayList<SearchResult>();
    }

    public List<SearchResult> retrieveWithTwoPoisson(String query) {
        // TODO : Membuat model Two Poisson
        return new ArrayList<SearchResult>();
    }
}
