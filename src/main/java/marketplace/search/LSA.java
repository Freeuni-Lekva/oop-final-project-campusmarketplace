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
    private final int R = 75;

    public LSA(List<Formatter> formatters, List<Post> posts){
        this.formatters = formatters;
        // Copies posts List to new List, So after formation, posts List won't change
        this.posts = new ArrayList<>();
        for (Post p : posts) {
            Post post = new Post(p.getProfile_id(), p.getPost_id(), p.getTitle(), p.getPrice(), p.getDescription(), p.getDate());
            this.posts.add(post);
        }
        formatAll(formatters);
        tf = new TF_IDF(this.posts);
        Matrix mm = tf.getTDM();
        System.out.println((int)tf.calculateVariance(mm));

        System.out.println();
        tf.setTDM(createLSAMatrix(tf));
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

    /** Formats all posts according to formatters list */
    private void formatAll(List<Formatter> formatters) {
        for(Formatter formatter : formatters) {
            for(Post post : posts){
                post.setDescription(formatter.format(post.getDescription()));
                post.setTitle(formatter.format(post.getTitle()));
            }
        }
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
            query = formatter.format(query);
        }
        List<Double> result = new ArrayList<>();

        for (Post post : posts) {
            result.add(evaluate(query, post));
        }

        return result;
    }

    @Override
    public Double evaluate(String query, Post post) {
        for(Formatter formatter : formatters) {
            query = formatter.format(query);
        }

        Matrix query_embedding = sentence_embedding(query);
        Matrix post_embedding = sentence_embedding(post.getTitle() + " " + post.getDescription());

        return cos_sim(query_embedding, post_embedding);
    }

    public Set<String> wordSet(){
        return tf.getWordSet();
    }
}
