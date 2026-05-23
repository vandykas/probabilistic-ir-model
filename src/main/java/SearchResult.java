public class SearchResult implements Comparable<SearchResult> {
    private final int docID;
    private final double score;

    public SearchResult(int docID, double score) {
        this.docID = docID;
        this.score = score;
    }

    @Override
    public int compareTo(SearchResult other) {
        return Double.compare(other.score, this.score);
    }

    @Override
    public String toString() {
        return String.format(
                "[DocID: %d, Score: %.3f]",
                docID,
                score
        );
    }
}
