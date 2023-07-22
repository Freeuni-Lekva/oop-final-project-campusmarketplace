package marketplace.search;

import Jama.Matrix;
import marketplace.objects.Post;

import java.util.*;

/** This class implements TF-IDF using TermFrequency class.
 * TF-IDF is used to determine the importance of Term (word) in Document (post)

 * It implements ScoringModel, so it is able to generate scores for posts according to query
 * */
public class TF_IDF extends TermFrequency implements ScoringModel{

    private final int K = 3;

    public TF_IDF(List<Post> posts) {
        super(posts);
        Map<String, Integer> documentFrequency = createDocumentFrequency();
        setTDM(createTFIDF(getTDM(), documentFrequency));
    }

    /** Creates main logic of TF-IDF. It uses already generated TF matrix
     * and then calculates new matrix values.

     * TF_IDF formula: TF_IDF(Term, Document) = TF(Term, Document) / log(N / DF(Term), where N is
     * number of documents

     * As you can see the formula that I'm using is slightly changed in terms of TF.

     * So the formula that I'm using is:  TF_IDF(T, D) = (TF(T, D) / (K + TF(T, D)) / log(N / DF(T) where K is
     * some constant
     * */
    private Matrix createTFIDF(Matrix tf, Map<String, Integer> df){
        int row = tf.getRowDimension();
        int col = tf.getColumnDimension();
        Map<Integer, String> wordIndexes = getIndexTerm();
        Matrix mat = new Matrix(row, col);

        for(int i = 0; i<row; i++){
            double df_val = df.get(wordIndexes.get(i));
            df_val = Math.log(col / df_val);
            for(int j = 0; j<col; j++){
                double tf_val = (tf.get(i, j) / (tf.get(i, j) + K));
                double tfidf_val = tf_val * df_val;
                mat.set(i, j, tfidf_val);
            }
        }
        return mat;
    }

    /** Returns true if post contains this word, otherwise false */
    private boolean containsWord(Post post, String word) {
        for(String post_word : post.getTitle().split("\\s+")) {
            if(post_word.equals(word)) return true;
        }

        for(String post_word : post.getDescription().split("\\s+")) {
            if(post_word.equals(word)) return true;
        }

        return false;
    }

    /** Creates Map of DF. Each pair of entry's (key, value) value tells us in
     *  how many documents does the word (key) appears */
    private Map<String, Integer> createDocumentFrequency() {
        List<Post> posts = getPosts();
        Set<String> words = getWordSet();

        Map<String, Integer> map = new HashMap<>();
        for(String word : words){
            map.put(word, 0);
            for(Post post : posts){
                if(containsWord(post,word))
                    map.put(word, map.get(word)+1);
            }
        }

        return map;
    }

    @Override
    public List<Double> evaluateAll(String query) {

        return null;
    }

    @Override
    public Double evaluate(String query, Post post) {
        return null;
    }
}
