import java.util.*;

public class Evaluator {
    private final Map<Integer, Set<Integer>> relevantDocuments;
    private List<PrecisionRecallPoint> precisionRecallPoints;

    public Evaluator() {
        this.relevantDocuments = new ReaderHelper().readRelevantDocuments();
    }

    public EvaluationResult evaluate(ProbabilisticModel model, ArrayList<String> queryTerms, int queryNum) {
        List<SearchResult> documentsRank = model.rankDocuments(queryTerms);
        this.precisionRecallPoints = calculatePrecisionRecallPoints(queryNum, documentsRank);
        return new EvaluationResult(
            precision(),
            recall(),
            precisionAtK(20),
            elevenPointAveragePrecision()
        );
    }

    private List<PrecisionRecallPoint> calculatePrecisionRecallPoints(int queryNum, List<SearchResult> documentsRank) {
        List<PrecisionRecallPoint> points = new ArrayList<>();
        int curTotalDoc = 0, curRelevantDoc = 0;
        Set<Integer> relevantDocs = relevantDocuments.getOrDefault(queryNum, new HashSet<>());
        int totalRelevantDoc = relevantDocs.size();

        for (SearchResult searchResult : documentsRank) {
            int docID = searchResult.docID();

            curTotalDoc++;
            if (relevantDocs.contains(docID)) {
                curRelevantDoc++;
            }

            double precision = (double) curRelevantDoc / curTotalDoc;
            double recall = (totalRelevantDoc == 0) ? 0 : (double) curRelevantDoc / totalRelevantDoc;
            points.add(
                    new PrecisionRecallPoint(
                            curTotalDoc,
                            precision,
                            recall,
                            docID
                    )
            );
        }
        return points;
    }

    private List<PrecisionRecallPoint> calculate11PointPrecision(){
        List<PrecisionRecallPoint> elevenPoints = new ArrayList<>();

        for(int i = 0; i <= 10; i++){
            double recallLevel = i / 10.0;
            double maxPrecision = 0.0;

            for(PrecisionRecallPoint point : precisionRecallPoints){
                if(point.recall() >= recallLevel){
                    maxPrecision = Math.max(maxPrecision, point.precision());
                }
            }

            elevenPoints.add(
                new PrecisionRecallPoint(
                    i,
                    maxPrecision,
                    recallLevel,
                    -1
                )
            );
        }

        return elevenPoints;
    }

    private double elevenPointAveragePrecision(){
        List<PrecisionRecallPoint> elevenPoints = calculate11PointPrecision();

        double sum = 0.0;
        for(PrecisionRecallPoint point : elevenPoints){
            sum += point.precision();
        }

        return sum/11.0;
    }

    private double precision() {
        if (precisionRecallPoints.isEmpty()) {
            return 0.0;
        }
        return precisionRecallPoints.getLast().precision();
    }

    private double recall() {
        if (precisionRecallPoints.isEmpty()) {
            return 0.0;
        }
        return precisionRecallPoints.getLast().recall();
    }

    private double precisionAtK(int k) {
        if (precisionRecallPoints.isEmpty()) {
            return 0.0;
        }

        if(precisionRecallPoints.size() < k){
            return precisionRecallPoints.getLast().precision();
        }

        return precisionRecallPoints.get(k - 1).precision();
    }
}
