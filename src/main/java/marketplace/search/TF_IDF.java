package marketplace.search;

import Jama.Matrix;
import marketplace.objects.Post;

import java.util.*;

/** This class implements TF-IDF using TermFrequency class.
 * TF-IDF is used to determine the importance of Term (word) in Document (post)

 * It implements ScoringModel, so it is able to generate scores for posts according to query
 * */
public class TF_IDF extends TermFrequency{

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

    public double calculateMean(Matrix m, int i){
        double mean = 0;
        for(int j = 0; j<m.getColumnDimension(); j++){
            mean += m.get(i,j);
        }
        mean /= (double) m.getColumnDimension();
        return mean;
    }

    public double calculateMean(List<Double> l){
        double mean = 0;
        for (Double aDouble : l) {
            mean += aDouble;
        }
        mean /= (double) l.size();
        return mean;
    }

    public double calculateVariance(Matrix matrix){
        List<Double> means = new ArrayList<>();
        for(int i = 0; i<matrix.getRowDimension(); i++)
            means.add(calculateMean(matrix,i));


        Matrix m = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for(int i = 0; i<matrix.getRowDimension(); i++){
            for(int j = 0; j<matrix.getColumnDimension(); j++){
                m.set(i,j,Math.pow(matrix.get(i,j)-means.get(i), 2));
            }
        }

        List<Double> squaredSums = new ArrayList<>();
        for(int i = 0; i<m.getRowDimension(); i++){
            double sum = 0;
            for(int j = 0; j<m.getColumnDimension(); j++){
                sum += m.get(i,j);
            }
            squaredSums.add(sum/(double) m.getColumnDimension());
        }


        double mean = calculateMean(squaredSums);
        double variance = 0;
        for(double i: squaredSums){
            variance+=Math.pow(i-mean, 2);
        }
        variance/=(double) squaredSums.size();
        return variance;
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
}
