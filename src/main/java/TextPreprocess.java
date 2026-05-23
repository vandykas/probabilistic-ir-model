public class TextPreprocess {
    private final Stemmer stemmer;

    public TextPreprocess() {
        this.stemmer = new Stemmer();
    }

    public String[] preprocess(String text) {
        String[] tokens = tokenize(text);
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = stemmer.stem(tokens[i]);
        }
        return tokens;
    }

    private String[] tokenize(String query) {
        query = query.toLowerCase();
        return query.split("[^a-z0-9]+");
    }
}
