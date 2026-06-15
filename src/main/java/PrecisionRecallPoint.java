public record PrecisionRecallPoint(
        int rank,
        double precision,
        double recall,
        int docID
) {}
