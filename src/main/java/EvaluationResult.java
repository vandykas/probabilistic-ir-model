public record EvaluationResult(
        double precision,
        double recall,
        double precisionAtK,
        double elevenPointInterpolatedAvg
) {
    @Override
    public String toString() {
        return String.format(
                "Precision: %.3f\nRecall: %.3f\nPrecision at k: %.3f\nEleven point interpolated avg: %.3f\n",
                precision,
                recall,
                precisionAtK,
                elevenPointInterpolatedAvg
        );
    }
}
