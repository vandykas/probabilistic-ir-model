import java.util.HashMap;
import java.util.Map;

public class Corpus {
    private final int documentCount;
    private final Map<String, Integer> documentsFrequency;
    private final int[] documentsLength;
    private double documentsAvgLength;

    public Corpus(int documentCount) {
        this.documentCount = documentCount;
        this.documentsFrequency = new HashMap<>();
        this.documentsLength = new int[documentCount + 1];
    }

    public int getDocumentCount() {
        return documentCount;
    }

    public Map<String, Integer> getDocumentsFrequency() {
        return documentsFrequency;
    }

    public void addDocumentFrequency(String term) {
        documentsFrequency.put(term, documentsFrequency.getOrDefault(term, 0) + 1);
    }

    public int[] getDocumentsLength() {
        return documentsLength;
    }

    public void setDocumentsLength(int docID, int length) {
        this.documentsLength[docID] = length;
    }

    public double getDocumentsAvgLength() {
        return documentsAvgLength;
    }

    public void setDocumentsAvgLength(double documentsAvgLength) {
        this.documentsAvgLength = documentsAvgLength;
    }
}
