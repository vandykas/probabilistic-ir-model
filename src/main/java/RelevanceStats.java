import java.util.Map;

public record RelevanceStats(int R, Map<String, Integer> relevantDocumentsTerm) {}
