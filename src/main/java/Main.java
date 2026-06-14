import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of documents used (1 - 1400): ");
        int documentCount = Integer.parseInt(sc.nextLine());

        ReaderHelper reader = new ReaderHelper();
        TextPreprocess textPreprocess = new TextPreprocess();

        DocumentsReader builder = new DocumentsReader(textPreprocess, reader);
        CollectionData collectionData = builder.buildCollectionData(documentCount);

        InvertedIndex invertedIndex = collectionData.invertedIndex();
        Corpus corpus = collectionData.corpus();
        PseudoRelevanceFeedback pseudoRel = new PseudoRelevanceFeedback(invertedIndex);

        while (true) {
            System.out.println("Use evaluation query (Y/N): ");
            if (sc.nextLine().equalsIgnoreCase("Y")) {
                System.out.println("Number of query to used (1 - 225): ");
                int queryCount = Integer.parseInt(sc.nextLine());
                evaluateMode(reader, invertedIndex, queryCount);
                break;
            }

            System.out.println("Enter your query: ");
            String query = sc.nextLine();
            System.out.println("Enter top k documents: ");
            int k = Integer.parseInt(sc.nextLine());
            normalMode(invertedIndex, corpus, textPreprocess, pseudoRel, query, k);
        }
    }

    public static void evaluateMode(ReaderHelper reader, InvertedIndex invertedIndex, int queryCount) {
        Map<Integer, String> queries = reader.readQueries();
        Evaluator evaluator = new Evaluator();
        for (Map.Entry<Integer, String> entry : queries.entrySet()) {
            int queryNum = entry.getKey();
            String query = entry.getValue();
        }
    }

    public static void normalMode(
            InvertedIndex invertedIndex,
            Corpus corpus,
            TextPreprocess textPreprocess,
            PseudoRelevanceFeedback pseudoRel,
            String query,
            int k) {
        RelevanceStats relevanceStats = pseudoRel.computeRelevanceStats(
                new BIM(invertedIndex, corpus, new WeightWithoutRelevance(corpus)),
                textPreprocess.preprocess(query),
                k);

        Map<String, ProbabilisticModel> probabilisticModels = new HashMap<>();
        probabilisticModels.put("BIM", new BIM(invertedIndex, corpus,
                new WeightWithRelevance(corpus, relevanceStats.R(), relevanceStats.relevantDocumentsTerm())));
        probabilisticModels.put("BM25", new BM25(
                invertedIndex,corpus,
                new WeightWithRelevance(corpus,relevanceStats.R(),relevanceStats.relevantDocumentsTerm())));

        probabilisticModels.put("Two-Poisson", new TwoPoisson(
                invertedIndex,
                corpus,
                new WeightWithRelevance(corpus,relevanceStats.R(),relevanceStats.relevantDocumentsTerm())));
        for (Map.Entry<String, ProbabilisticModel> entry : probabilisticModels.entrySet()) {
            String modelName = entry.getKey();
            ProbabilisticModel model = entry.getValue();
            List<SearchResult> documentsRank = model.rankDocuments(textPreprocess.preprocess(query));

            printRanking(modelName, documentsRank);
        }
    }

    public static void printRanking(String modelName, List<SearchResult> documentsRank) {
        System.out.println("Ranking for " + modelName + ":");
        for (SearchResult searchResult : documentsRank) {
            System.out.println(searchResult);
        }
    }
}