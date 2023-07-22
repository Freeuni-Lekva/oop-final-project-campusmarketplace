package marketplace.search;

import Jama.Matrix;
import marketplace.objects.Post;

import java.util.*;

/** This class implements the main logic of TF. Then
 *  it can be extended to TF-IDF, BM25...
 * */
public class TermFrequency {
    private final List<Post> posts;
    private Matrix termDocumentMatrix;
    /** Saves Information for how word -> index appears in termDocumentMatrix */
    private final Map<String, Integer> term_Index;
    /** Saves Information for how index -> word appears in termDocumentMatrix */
    private final Map<Integer, String> index_term;
    private final Set<String> wordSet;
    public TermFrequency(List<Post> posts){
        this.posts = posts;
        Map<String, List<Integer>> termDocumentMap = createTermDocumentMap(posts);
        term_Index = new TreeMap<>();
        index_term = new TreeMap<>();
        wordSet = new HashSet<>();
        termDocumentMatrix = createTDM(termDocumentMap);
    }

    /** Creates Term Frequency Matrix. Each (i,j) element says how many i-th word appears in j-th post*/
    private Matrix createTDM(Map<String, List<Integer> > map){
        Matrix mat = new Matrix(map.size(), posts.size());
        int wordIndexes = 0;
        for(Map.Entry<String, List<Integer> > entry : map.entrySet()){
            for(int postIndexes : entry.getValue()){
                mat.set(wordIndexes, postIndexes, mat.get(wordIndexes, postIndexes) + 1);
            }
            term_Index.put(entry.getKey(), wordIndexes);
            index_term.put(wordIndexes, entry.getKey());
            wordIndexes++;
        }
        return mat;
    }

    /** Creates map that has all the words as a key and List of post indexes as a value. If word
     * appears more than once in a post, then there will be two indexes of this post in the value */
    private Map<String, List<Integer> > createTermDocumentMap(List<Post> posts){
        Map<String, List<Integer> > map = new TreeMap<>();
        for(int i = 0; i<posts.size(); i++){
            // Adds Post indexes to words value
            for(String word : posts.get(i).getTitle().split("\\s+")) {
                wordSet.add(word);
                if (!map.containsKey(word))
                    map.put(word, new ArrayList<Integer>());
                map.get(word).add(i);
            }
            for(String word : posts.get(i).getDescription().split("\\s+")) {
                wordSet.add(word);
                if (!map.containsKey(word))
                    map.put(word, new ArrayList<Integer>());
                map.get(word).add(i);
            }
        }
        return map;
    }

    public Set<String> getWordSet() {
        return wordSet;
    }


    /** Gets row matrix of particular query */
    public Matrix getWordEmbedding(String query) {
        if(term_Index.get(query) == null) return null;
        return termDocumentMatrix.getMatrix(term_Index.get(query), term_Index.get(query), 0, posts.size()-1);
    }

    public Matrix getTDM() {
        return termDocumentMatrix;
    }

    public void setTDM(Matrix matrix){
        termDocumentMatrix = matrix;
    }

    public Map<Integer, String> getIndexTerm() {
        return index_term;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
