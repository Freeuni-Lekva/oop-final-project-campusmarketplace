package marketplace.search;

import marketplace.objects.Post;

import java.util.List;

public class SearchEngine {
    private final List<ScoringModel> models;
    private final List<Post> posts;
    private final List<Formatter> formatters;
    public SearchEngine(List<ScoringModel> models, List<Post> posts, List<Formatter> formatters){
        this.models = models;
        this.posts = posts;
        this.formatters = formatters;
    }

    public List<Post> execute(String query){
        return null;
    }
}
