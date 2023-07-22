package marketplace.search;

import marketplace.objects.Post;

import java.util.List;

public interface ScoringModel {

    /** Evaluates all posts and returns List, where i-th index refers to i-th post */
    List<Double> evaluateAll(String query);

    /** Calculates score of individual post according to query */
    Double evaluate(String query, Post post);
}
