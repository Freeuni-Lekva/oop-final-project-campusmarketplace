package marketplace.search;
import Jama.Matrix;
import marketplace.objects.Post;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  This class implements Latent Semantic Analysis. It uses SVD Decomposition
 *  and Dimension Reduction methods to learn automatically synonyms of terms.

 *  This class implements ScoringModel, so it is able to return scores for posts
 *  according to query
 * */
public class LSA implements ScoringModel{
    private final List<Formatter> formatters;
    private final List<Post> posts;
    private final TermFrequency tf;

    public LSA(List<Formatter> formatters, List<Post> posts){
        this.formatters = formatters;
        // Copies posts List to new List, So after formation, posts List won't change
        this.posts = new ArrayList<>();
        Collections.copy(posts, this.posts);
        formatAll(formatters);
        tf = new TF_IDF(posts);
        tf.setTDM(createLSAMatrix(tf));
    }

    /** It implements main logic of LSA algorithm */
    private Matrix createLSAMatrix(TermFrequency tf) {
        return null;
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

    @Override
    public List<Double> evaluateAll(String query) {
        for(Formatter formatter : formatters) {
            query = formatter.format(query);
        }
        List<Double> result = new ArrayList<>(posts.size());

        for(int i = 0; i<posts.size(); i++){
            result.set(i, evaluate(query, posts.get(i)));
        }

        return result;
    }

    @Override
    public Double evaluate(String query, Post post) {
        return null;
    }
}
