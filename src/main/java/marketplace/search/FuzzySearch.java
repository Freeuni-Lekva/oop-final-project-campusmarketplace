package marketplace.search;

import com.mysql.cj.exceptions.WrongArgumentException;
import marketplace.objects.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        int min_score = 0;
        int word_counter = 0;
        for(String word1 : sentence1.split("\\s+")) {
            int cur_score = Integer.MAX_VALUE;
            word_counter++;
            for(String word2 : sentence2.split("\\s+")) {
                cur_score = min(cur_score, levenshtein.editScore(word1,word2));
            }
            min_score += cur_score;
        }
        return (double) word_counter/(double) min_score;
    }
}
