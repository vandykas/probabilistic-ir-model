public record SearchResult(int docID, double score) implements Comparable<SearchResult> {

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
