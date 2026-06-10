import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of documents used (1 - 1400): ");
        int documentCount = Integer.parseInt(sc.nextLine());

        ReaderHelper reader = new ReaderHelper();
        InvertedIndex invertedIndex = new InvertedIndex(documentCount, reader);
        ProbabilisticRetrieval retrieval = new ProbabilisticRetrieval(invertedIndex, documentCount);
        while (true) {
            System.out.println("Enter your query: ");
            String query = sc.nextLine();

            List<SearchResult> results = retrieval.retrieveWithBIM(query);
            for (SearchResult result : results) {
                System.out.println(result);
            }
        }
    }
}