import java.util.Map;
import java.util.Set;

public class Evaluator {
    private final ReaderHelper reader;
    private Map<Integer, Set<Integer>> relevantDocuments;
    private Map<Integer, String> queries;

    public Evaluator(ReaderHelper reader) {
        this.reader = reader;
        relevantDocuments = reader.readRelevantDocument();
        queries = reader.readQueries();
    }
}
