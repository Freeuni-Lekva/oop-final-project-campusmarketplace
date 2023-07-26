package marketplace.search;

import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.Post;
import java.util.*;

/** This class is the wrapper class for all search methods and formatters */
public class SearchEngine {
    private ScoringModel lsa;
    private Set<String> word_set;
    private List<Post> posts;
    private List<Post> lsa_posts;
    private final Map<String, List<Formatter> > formatters;
    private final PostDAO postDAO;
    public SearchEngine(PostDAO postDAO){
        this.postDAO = postDAO;
        this.posts = getAllPosts(postDAO);
        // Copies posts List to new List, So after formation, posts List won't change
        this.lsa_posts = deepCopy(this.posts);
        this.formatters = new TreeMap<>();
        this.word_set = createWordSet(this.posts);
        // Creates Formatters for LSA
        Formatter lemma = new Lemmatization(this.word_set);
        formatters.put("LSA", new ArrayList<>());
        formatters.get("LSA").add(lemma);
        System.out.println("Finished Lemma!");
        format_for(formatters, lsa_posts, "LSA");
        lsa = new LSA(formatters.get("LSA"), lsa_posts);
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
        formatters.put("LSA", new ArrayList<>());
        formatters.get("LSA").add(lemma);
        System.out.println("Finished Lemma!");
        format_for(formatters, lsa_posts, "LSA");
        lsa = new LSA(formatters.get("LSA"), lsa_posts);
    }

    /** Prints LSA score for debugging */
    public void get_score(String q1, String q2){
        System.out.print(q1+", "+q2+":");
        System.out.print(lsa.evaluate(q1, q2));
        System.out.println();
    }

    /** Main method of the search engine, that returns num posts for a query */
    public List<Post> execute(String query, int num){
        List<Double> scores = lsa.evaluateAll(query);
        List<Post> answer = new ArrayList<>();

        Map<Double, List<Post> > map = new TreeMap<>();
        for(int i = 0; i<scores.size(); i++){
            if(!map.containsKey(-scores.get(i))){
                map.put(-scores.get(i), new ArrayList<>());
            }
            map.get(-scores.get(i)).add(lsa_posts.get(i));
        }

        for(Double key: map.keySet()){
            for(Post post : map.get(key)){
                if(num==0) break;
                answer.add(post);
                num--;
            }
        }
        return answer;
    }
}
