package marketplace.search;

import junit.framework.TestCase;
import marketplace.constants.DatabaseConstants;
import marketplace.dao.PostDAO;
import marketplace.objects.Post;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoringTest extends TestCase {
    private SearchEngine searchEngine;
    private FuzzySearch fuzzySearch;
    @Override
    protected void setUp() throws Exception {
        try {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/" + DatabaseConstants.DATABASE_NAME);
            dataSource.setUsername(DatabaseConstants.DATABASE_USERNAME);
            dataSource.setPassword(DatabaseConstants.DATABASE_PASSWORD);

            PostDAO postDAO = new PostDAO(dataSource);
            searchEngine = new SearchEngine(postDAO);
        } catch (Exception e){
            throw new RuntimeException("Database connection error");
        }
        Levenshtein levenshtein = new Levenshtein(searchEngine.getWord_set(), 4);
        fuzzySearch = new FuzzySearch(levenshtein, searchEngine.getPosts());
    }

    public void testFuzzy(){
        String query = "ოუფნის ბილეთები", post = "ვყიდი ოუფენის ბილეთებს ძალიან დაბალ ფასებში";

        assertEquals((6.0/7.0 + 7.0/8.0)/2.0, fuzzySearch.evaluate(query, post));
        Post p1 = new Post(1,1,post, 100,"კაი პონტია", new Date(2003,3,6));
        Post p2 = new Post(1,1,"ვყიდი კომპიუტერს", 100,"თესლი კომპია", new Date(2003,3,6));
        assertEquals(fuzzySearch.evaluate(query, p1), fuzzySearch.evaluate(query, post));

        List<Post> posts = new ArrayList<>();
        posts.add(p1); posts.add(p2);

        fuzzySearch = new FuzzySearch(new Levenshtein(searchEngine.getWord_set(), 4), posts);
        assertEquals(1, fuzzySearch.evaluateAll(query).stream().filter(i->i>0.5).collect(Collectors.toList()).size());
    }

    public void testLSA(){
        LSA lsa = new LSA(new ArrayList<>(), searchEngine.getPosts());
        assertTrue(lsa.evaluate("ოუფენის ბილეთები", "ბილეთი")>0.5);
        Post p1 = new Post(1,1,"ოუფენის ბილეთები", 100,"კაი პონტია", new Date(2003,3,6));
        assertTrue(lsa.evaluate("ოუფენის", p1)>=0.5);
    }

    public void testSearchEngine(){
        List<Post> answers = searchEngine.execute("ოუფენის ბილეთები", 15);
        searchEngine.update();
        List<Post> second_answers = searchEngine.execute("ოუფენის ბილეთები", 15);
        for(int i = 0; i<answers.size(); i++) {
            assertEquals(answers.get(i).getPost_id(), second_answers.get(i).getPost_id());
        }
    }

}
