import java.util.*;

public abstract class ProbabilisticModel {
    protected final InvertedIndex invertedIndex;
    protected final Corpus corpus;

    public ProbabilisticModel(InvertedIndex invertedIndex,  Corpus corpus) {
        this.invertedIndex = invertedIndex;
        this.corpus = corpus;
    }

    public List<SearchResult> rankDocuments(ArrayList<String> queryTerms) {
        Map<Integer, Double> documentsScore = new HashMap<>();

        for (String term : queryTerms) {
            if (!invertedIndex.isContainsTerm(term)) {
                continue;
            }

            Map<Integer, Integer> postingList = invertedIndex.getPostingList(term);

            for (Map.Entry<Integer, Integer> entry : postingList.entrySet()) {
                int docId = entry.getKey();
                int tf = entry.getValue();

                double score = calculateScore(term, tf, docId);
                documentsScore.put(docId, documentsScore.getOrDefault(docId, 0.0) + score);
            }
        }

        List<SearchResult> documentRanking = new ArrayList<>();

        for (Map.Entry<Integer, Double> entry : documentsScore.entrySet()) {
            documentRanking.add(new SearchResult(entry.getKey(), entry.getValue()));
        }

        sortDocumentsRank(documentRanking);
        return documentRanking;

    }

    protected abstract double calculateScore(String term, int tf, int docId);

    protected void sortDocumentsRank(List<SearchResult> results) {
        Collections.sort(results);
    }
}
