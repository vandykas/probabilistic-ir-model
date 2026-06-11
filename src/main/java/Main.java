import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of documents used (1 - 1400): ");
        int documentCount = Integer.parseInt(sc.nextLine());

        ReaderHelper reader = new ReaderHelper();
        InvertedIndex invertedIndex = new InvertedIndex(documentCount, reader);
        ProbabilisticModel[] models = {
                new BIM(invertedIndex, documentCount)
        };

        while (true) {
            System.out.println("Use evaluation query (Y/N): ");
            if (sc.nextLine().equals("Y")) {
                System.out.println("Number of query to used (1 - 225): ");
                int queryCount = Integer.parseInt(sc.nextLine());
                evaluateMode(reader, invertedIndex, models, queryCount);
                break;
            }

            System.out.println("Enter your query: ");
            String query = sc.nextLine();
        }
    }

    public static void evaluateMode(ReaderHelper reader, InvertedIndex invertedIndex, ProbabilisticModel[] models, int queryCount) {
        Map<Integer, String> queries = reader.readQueries();
        Evaluator evaluator = new Evaluator();
        for (Map.Entry<Integer, String> entry : queries.entrySet()) {
            int queryNum =  entry.getKey();
            String query = entry.getValue();

            EvaluationResult[] results = new EvaluationResult[models.length];
            for (int i = 0; i < models.length; i++) {
                results[i] = evaluator.evaluate(models[i], queryNum, query);
            }
        }
    }

    public static void normalMode() {

    }
}