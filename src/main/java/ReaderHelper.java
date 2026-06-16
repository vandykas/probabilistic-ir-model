import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReaderHelper {
    public String readDocument(int docId) {
        Path document = Path.of("src/main/resources/" + docId + ".txt");
        StringBuilder builder = new StringBuilder();
        if (Files.exists(document)) {
            try (BufferedReader reader = Files.newBufferedReader(document)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        return builder.toString();
    }

    public Map<Integer, Set<Integer>> readRelevantDocuments() {
        Map<Integer, Set<Integer>> relevantDocuments = new HashMap<>();
        Path document = Path.of("src/main/resources/cranqrel");
        if (Files.exists(document)) {
            try (BufferedReader reader = Files.newBufferedReader(document)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(" ");
                    int query = Integer.parseInt(words[0]);
                    int documentId = Integer.parseInt(words[1]);
                    relevantDocuments.putIfAbsent(query, new HashSet<>());
                    relevantDocuments.get(query).add(documentId);
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        return relevantDocuments;
    }

    public Map<Integer, String> readQueries() {
        Map<Integer, String> queries = new HashMap<>();
        Path document = Path.of("src/main/resources/cran.qry");
        StringBuilder builder = new StringBuilder();

        int queryNumber = -1;
        if (Files.exists(document)) {
            try (BufferedReader reader = Files.newBufferedReader(document)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(".I")) {
                        if (queryNumber != -1) {
                            queries.put(queryNumber, builder.toString());
                            builder = new StringBuilder();
                        }
                        queryNumber = Integer.parseInt(line.split(" ")[1]);
                    }
                    else if (!line.startsWith(".W")) {
                        builder.append(line).append(" ");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        queries.put(queryNumber, builder.toString());
        return queries;
    }
}
