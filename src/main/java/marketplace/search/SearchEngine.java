package marketplace.search;

import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.Post;
import java.util.*;

/** This class is the wrapper class for all search methods and formatters */
public class SearchEngine {
    private ScoringModel lsa;
    private ScoringModel fuzzy_search;
    private Set<String> word_set;
    private List<Post> posts;
    private List<Post> lsa_posts;
    private final Map<String, List<Formatter> > formatters;
    private final PostDAO postDAO;
    private final int LIMIT = 4;
    private final double THRESHOLD = 0.5;
    private final double LSA_WEIGHT = 0.55;
    private final double FUZZY_WEIGHT = 0.45;
    public SearchEngine(PostDAO postDAO){
        this.postDAO = postDAO;
        this.posts = getAllPosts(postDAO);
        // Copies posts List to new List, So after formation, posts List won't change
        this.lsa_posts = deepCopy(this.posts);
        this.formatters = new TreeMap<>();
        this.word_set = createWordSet(this.posts);
        // Creates Formatters
        Formatter lemma = new Lemmatization(this.word_set);

        Levenshtein levenshtein = new Levenshtein(this.word_set, LIMIT);

        formatters.put("LSA", new ArrayList<>());
        formatters.get("LSA").add(lemma);

        format_for(formatters, lsa_posts, "LSA");
        formatters.get("LSA").add(0, levenshtein);
        lsa = new LSA(formatters.get("LSA"), lsa_posts);
        fuzzy_search = new FuzzySearch(levenshtein, this.posts);
    }

    private Set<String> createWordSet(List<Post> posts) {
        Set<String> words = new TreeSet<>();
        for(Post p : posts){
            words.addAll(Arrays.asList(p.getTitle().split("\\s+")));
            words.addAll(Arrays.asList(p.getDescription().split("\\s+")));
        }
        return words;
    }

    private List<Post> getAllPosts(PostDAO postDAO){
        List<Post> posts = new ArrayList<>();

        for(int j = 0; ; j++){
            List<FeedPost> feedPostList = postDAO.getAllFeedPosts(j);
            if(feedPostList==null || feedPostList.size()==0) break;

            for(FeedPost i : feedPostList){
                posts.add(postDAO.getPostById(i.getPost_id()));
            }
        }

        return posts;
    }

    private List<Post> deepCopy(List<Post> posts){
        List<Post> lsa_posts = new ArrayList<>();
        for (Post p : posts) {
            Post post = new Post(p.getProfile_id(), p.getPost_id(), p.getTitle(), p.getPrice(), p.getDescription(), p.getDate());
            lsa_posts.add(post);
        }
        return lsa_posts;
    }

    /** Formats all posts according to formatters list */
    private List<Post> format_for(Map<String, List<Formatter> > formatters, List<Post> posts, String key) {
        for(Formatter formatter : formatters.get(key)) {
            for(Post post : posts){
                post.setDescription(formatter.formatAll(post.getDescription()));
                post.setTitle(formatter.formatAll(post.getTitle()));
            }
        }
        return posts;
    }

    /** This method is called when new post is added, delete or edited */
    public void update(){
        this.posts = getAllPosts(postDAO);
        this.lsa_posts = deepCopy(this.posts);
        this.word_set = createWordSet(this.posts);
        Formatter lemma = new Lemmatization(this.word_set);

        Levenshtein levenshtein = new Levenshtein(this.word_set, LIMIT);

        formatters.put("LSA", new ArrayList<>());
        formatters.get("LSA").add(lemma);
        format_for(formatters, lsa_posts, "LSA");

        formatters.get("LSA").add(0, levenshtein);

        lsa = new LSA(formatters.get("LSA"), lsa_posts);
        fuzzy_search = new FuzzySearch(levenshtein, this.posts);
    }

    /** Prints LSA score for debugging */
    public void get_score(String q1, String q2){
        System.out.print(q1+", "+q2+":");
        System.out.print(lsa.evaluate(q1, q2));
        System.out.println();
    }

    public List<Post> getPosts() {
        return posts;
    }
    public Set<String> getWord_set() {
        return word_set;
    }

    /** This method combines different scores by their weights.
     *  diffScores and weights MUST have the same size, otherwise it will throw an error
     *  diffScores every element's size must be equal to each other, otherwise it will throw an error
     * */
    private List<Double> hybridSearch(List<List<Double>> diffScores, List<Double> weights) {
        if(weights.size() != diffScores.size()) throw new IllegalArgumentException("weights and diffScores doesn't have same size");
        for(int i = 0; i<diffScores.size()-1; i++) {
            if(diffScores.get(i).size() != diffScores.get(i+1).size())
                throw new IllegalArgumentException("In diffScores, every element must have equal sizes");
        }

        List<Double> final_scores = new ArrayList<>();
        for(int i = 0; i<diffScores.get(0).size(); i++){
            final_scores.add(0.0);
        }

        for(int i = 0; i<diffScores.size(); i++){
            List<Double> cur_scores = diffScores.get(i);
            double cur_weight = weights.get(i);
            for(int j = 0; j<cur_scores.size(); j++){
                final_scores.set(j, (final_scores.get(j) + cur_scores.get(j) * cur_weight));
            }
        }

        return final_scores;
    }

    /** Main method of the search engine, that returns num posts for a query */
    public List<Post> execute(String query, int num){
        // Generates LSA and Fuzzy scores
        List<Double> lsa_scores = lsa.evaluateAll(query);
        List<Double> fuzzy_scores = fuzzy_search.evaluateAll(query);
        // Creates Weights for LSA and Fuzzy to combine them
        List<List<Double>> diff_scores = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        diff_scores.add(lsa_scores); weights.add(LSA_WEIGHT);
        diff_scores.add(fuzzy_scores); weights.add(FUZZY_WEIGHT);
        // Combining scores
        List<Double> scores = hybridSearch(diff_scores, weights);

        // Sorting posts descending order by combined scores
        List<Post> answer = new ArrayList<>();
        Map<Double, List<Post> > map = new TreeMap<>();
        for(int i = 0; i<scores.size(); i++){
            if(!map.containsKey(-scores.get(i))){
                map.put(-scores.get(i), new ArrayList<>());
            }
            map.get(-scores.get(i)).add(posts.get(i));
        }

        for(Double key: map.keySet()){
            if(-key < THRESHOLD) break;
            for(Post post : map.get(key)){
                if(num==0) break;
                answer.add(post);
                num--;
            }
        }
        return answer;
    }
}