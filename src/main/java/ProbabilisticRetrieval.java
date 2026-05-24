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
}
