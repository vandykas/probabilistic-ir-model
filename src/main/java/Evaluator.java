import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Evaluator {
    private final Map<Integer, Set<Integer>> relevantDocuments;
    private List<PrecisionRecallPoint> precisionRecallPoints;

    public Evaluator() {
        this.relevantDocuments = new ReaderHelper().readRelevantDocument();
    }

    public EvaluationResult evaluate(ProbabilisticModel model, int queryNum, String query) {
        TextPreprocess textPreprocess = TextPreprocess.getInstantiation();
        List<SearchResult> documentsRank = model.rankDocuments(textPreprocess.preprocess(query));

        this.precisionRecallPoints = calculatePrecisionRecallPoints(queryNum, documentsRank);
        return new EvaluationResult(
            precision(),
            precisionAtK(5),
                0
        );
    }

    private List<PrecisionRecallPoint> calculatePrecisionRecallPoints(int queryNum, List<SearchResult> documentsRank) {
        List<PrecisionRecallPoint> precisionRecallPoints = new ArrayList<>();
        int curTotalDoc = 0, curRelevantDoc = 0;
        for (SearchResult searchResult : documentsRank) {
            int docID = searchResult.docID();

            curTotalDoc++;
            if (relevantDocuments.get(queryNum).contains(docID)) {
                curRelevantDoc++;
            }

            precisionRecallPoints.add(
                    new PrecisionRecallPoint(
                            docID,
                            (double) curRelevantDoc / curTotalDoc,
                            curTotalDoc
                    )
            );
        }
        return precisionRecallPoints;
    }

    public double precision() {
        return precisionRecallPoints.getLast().precision();
    }

    public double precisionAtK(int k) {
        return precisionRecallPoints.get(k - 1).precision();
    }
}
