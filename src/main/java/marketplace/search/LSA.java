package marketplace.search;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import marketplace.objects.Post;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *  This class implements Latent Semantic Analysis. It uses SVD Decomposition
 *  and Dimension Reduction methods to learn automatically synonyms of terms.

 *  This class implements ScoringModel, so it is able to return scores for posts
 *  according to query
 * */
public class LSA implements ScoringModel{
    private final List<Formatter> formatters;
    private final List<Post> posts;
    private final TF_IDF tf;

    /** Compresses SVD To R */
    private int R = 0;

    public LSA(List<Formatter> formatters, List<Post> posts){
        this.formatters = formatters;
        this.posts = posts;
        tf = new TF_IDF(this.posts);
        System.out.println(tf.getTDM().rank());
        R = tf.getTDM().rank()/2;
        tf.setTDM(createLSAMatrix(tf));
        System.out.println(tf.getTDM().rank());
    }

    /** It implements main logic of LSA algorithm */
    private Matrix createLSAMatrix(TermFrequency tf) {
        Matrix tdm = tf.getTDM();
        if(tdm.getColumnDimension() < R || tdm.getRowDimension() < R) return tdm;

        SingularValueDecomposition svd = tdm.svd();
        Matrix u = svd.getU();
        Matrix s = svd.getS();
        Matrix v = svd.getV();

        s = s.getMatrix(0, R,0, R);
        u = u.getMatrix(0, u.getRowDimension()-1, 0, R);
        v = v.getMatrix(0, v.getRowDimension()-1, 0 , R);


        Matrix us = u.times(s);
        us = us.times(v.transpose());
        return us;
    }

    public double dot_product(Matrix u, Matrix v){
        double ans = 0;

        for(int i = 0; i<u.getColumnDimension(); i++){
            ans += u.get(0, i) * v.get(0, i);
        }

        return ans;
    }

    public double vector_len(Matrix u){
        double ans = 0;

        for(int i = 0; i<u.getColumnDimension(); i++){
            ans += Math.pow(u.get(0,i), 2);
        }

        return Math.sqrt(ans);
    }

    public double cos_sim(Matrix u, Matrix v){
        return dot_product(u,v)/(vector_len(u) * vector_len(v));
    }

    public Matrix sentence_embedding(String sentence){
        return sentence_embedding(sentence, true);
    }

    public Matrix sentence_embedding(String sentence, boolean do_format){
        if(do_format) {
            for (Formatter f : formatters) sentence = f.formatAll(sentence);
        }
        Matrix embedding = new Matrix(1, posts.size());
        for(String word : sentence.split("\\s+")){
            Matrix mat = tf.getWordEmbedding(word);
            if(mat==null) continue;
            embedding = embedding.plus(mat);
        }
        return embedding;
    }

    @Override
    public List<Double> evaluateAll(String query) {
        for(Formatter formatter : formatters) {
            query = formatter.formatAll(query);
        }
        List<Double> result = new ArrayList<>();
        for (Post post : posts) {
            result.add(evaluate(query, post, false));
        }
        return result;
    }

    @Override
    public Double evaluate(String query, Post post) {
        return evaluate(query, post, true);
    }

    public Double evaluate(String query, Post post, boolean do_format) {
        if(do_format) {
            for (Formatter formatter : formatters) {
                query = formatter.formatAll(query);
            }
        }

        Matrix query_embedding = sentence_embedding(query, do_format);
        Matrix post_embedding = sentence_embedding(post.getTitle() + " " + post.getDescription(), do_format);

        return cos_sim(query_embedding, post_embedding);
    }

    @Override
    public Double evaluate(String sentence1, String sentence2) {
        return evaluate(sentence1, sentence2, true);
    }

    public Double evaluate(String sentence1, String sentence2, boolean do_format) {
        if(do_format) {
            for (Formatter formatter : formatters) {
                sentence1 = formatter.formatAll(sentence1);
                sentence2 = formatter.formatAll(sentence2);
            }
        }

        Matrix sent1_embedding = sentence_embedding(sentence1, do_format);
        Matrix sent2_embedding = sentence_embedding(sentence2, do_format);

        return cos_sim(sent1_embedding, sent2_embedding);
    }

    public Set<String> wordSet(){
        return tf.getWordSet();
    }
}
