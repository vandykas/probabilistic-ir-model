import java.util.*;
import java.util.stream.Collectors;

public class PseudoRelevanceFeedback {
    private final InvertedIndex invertedIndex;

    public PseudoRelevanceFeedback(InvertedIndex invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public RelevanceStats computeRelevanceStats(ProbabilisticModel model, ArrayList<String> terms, int k) {
        List<SearchResult> documentsRank = model.rankDocuments(terms);
        Set<Integer> pseudoRelevantDocs = documentsRank
                .stream()
                .limit(k)
                .map(SearchResult::docID)
                .collect(Collectors.toSet());

        Map<String, Integer> relevantDocumentsTerm = new HashMap<>();
        for (String term : terms) {
            Map<Integer, Integer> postingList = invertedIndex.getPostingList(term);
            int relevantCount = 0;
            if (postingList != null) {
                for (Integer docId : pseudoRelevantDocs) {
                    if (postingList.containsKey(docId)) {
                        relevantCount++;
                    }
                }
            }
            relevantDocumentsTerm.put(term, relevantCount);
        }
        return new RelevanceStats(Math.min(k, documentsRank.size()), relevantDocumentsTerm);
    }
}
