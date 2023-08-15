package marketplace.search;

import marketplace.objects.Post;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class FuzzySearch implements ScoringModel{
    private final Levenshtein levenshtein;
    private final List<Post> posts;
    public FuzzySearch(Levenshtein levenshtein, List<Post> posts){
        this.levenshtein = levenshtein;
        this.posts = posts;
    }

    @Override
    public List<Double> evaluateAll(String query) {
        List<Double> scores = new ArrayList<>();
        for(Post post : posts){
            scores.add(evaluate(query, post));
        }
        return scores;
    }

    @Override
    public Double evaluate(String query, Post post) {
        return evaluate(query, post.getTitle() + " " + post.getDescription());
    }

    @Override
    public Double evaluate(String sentence1, String sentence2) {
        double min_score = 0;
        int word_counter = 0;
        for(String word1 : sentence1.split("\\s+")) {
            double cur_score = Integer.MIN_VALUE;
            word_counter++;
            for(String word2 : sentence2.split("\\s+")) {
                if(word2.length()==0) continue;


                int mini = min(word1.length(), word2.length());
                int maxi = max(word1.length(), word2.length());
                cur_score = max(cur_score, (double) ((mini + maxi-mini) - levenshtein.editScore(word1, word2)) /((mini + maxi-mini)));
            }
            min_score += cur_score;
        }
        return min_score / (double) word_counter;
    }
}
