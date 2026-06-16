import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {
    private final Map<String, Map<Integer, Integer>> invertedIndex;

    public InvertedIndex() {
        this.invertedIndex = new HashMap<>();
    }

    public void putDocument(String term, int docID) {
        invertedIndex.putIfAbsent(term, new HashMap<>());
        invertedIndex.get(term).put(docID, invertedIndex.get(term).getOrDefault(docID, 0) + 1);
    }

    public Map<Integer, Integer> getPostingList(String term) {
        return invertedIndex.getOrDefault(term, new HashMap<>());
    }

    public boolean isContainsTerm(String term) {
        return invertedIndex.containsKey(term);
    }

    public void print() {
        for (Map.Entry<String, Map<Integer, Integer>> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            Map<Integer, Integer> postings = entry.getValue();

            System.out.println(term + " -> ");

            for (Map.Entry<Integer, Integer> posting : postings.entrySet()) {
                int docId = posting.getKey();
                int tf = posting.getValue();

                System.out.println("{id: " + docId + ", tf: " + tf + "} ");
            }
        }
    }
}