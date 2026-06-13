import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DocumentsReader {
    private final TextPreprocess textPreprocess;
    private final ReaderHelper reader;

    public DocumentsReader(TextPreprocess textPreprocess, ReaderHelper reader) {
        this.textPreprocess = textPreprocess;
        this.reader = reader;
    }

    public CollectionData buildCollectionData(int documentCount) {
        Set<String> uniqueTokens = new HashSet<>();
        ArrayList<String> tokens;
        InvertedIndex invertedIndex = new InvertedIndex();
        Corpus corpus = new Corpus(documentCount);

        double documentLengthTotal = 0;
        for (int docId = 1; docId <= documentCount; docId++) {
            String content = reader.readDocument(docId);
            tokens = textPreprocess.preprocess(content);

            int documentLength = tokens.size();
            corpus.setDocumentsLength(docId,  documentLength);
            documentLengthTotal += documentLength;

            for (String term : tokens) {
                invertedIndex.putDocument(term, docId);
                if (!uniqueTokens.contains(term)) {
                    corpus.addDocumentFrequency(term);
                    uniqueTokens.add(term);
                }
            }
            uniqueTokens.clear();
        }
        corpus.setDocumentsAvgLength(documentLengthTotal / documentCount);

        return new CollectionData(corpus, invertedIndex);
    }
}
