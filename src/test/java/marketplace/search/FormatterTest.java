package marketplace.search;

import junit.framework.TestCase;
import marketplace.constants.DatabaseConstants;
import marketplace.dao.PostDAO;
import org.apache.commons.dbcp2.BasicDataSource;

public class FormatterTest extends TestCase {
    private Lemmatization lemmatization;
    private Levenshtein levenshtein;
    private SearchEngine searchEngine;
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
        levenshtein = new Levenshtein(searchEngine.getWord_set(), 4);
        lemmatization = new Lemmatization(searchEngine.getWord_set());
    }

    public void testLemma(){
        String word = "ბილეთები", sentence = "ბილეთები ბილეთები", ans_word = "ბილეთი", ans_sent = "ბილეთი ბილეთი";
        String garbage_en = "anfjasn", garbage_ka = "ბაგრვავვა";
        String garbage_sent_en = "argvaejkc argve", garbage_sent_ka = "გაერჯვაჯ არგვაეჯცნჯ";

        assertEquals(lemmatization.formatAll(word), lemmatization.format(word));
        assertEquals(ans_sent, lemmatization.formatAll(sentence));
        assertEquals(ans_word, lemmatization.format(word));

        assertEquals(lemmatization.formatAll(garbage_en), lemmatization.format(garbage_en));
        assertEquals(lemmatization.formatAll(garbage_ka), lemmatization.format(garbage_ka));
        assertEquals(garbage_sent_en, lemmatization.formatAll(garbage_sent_en));
        assertEquals(garbage_sent_ka, lemmatization.formatAll(garbage_sent_ka));
        assertEquals(garbage_en, lemmatization.format(garbage_en));
        assertEquals(garbage_ka, lemmatization.format(garbage_ka));
    }


    public void testLevenshtein(){
        String word1 = "kitten", word2 = "sitting";
        String typo1 = "მასწავლებწლი", typo2 = "მასწვლებწლი", typo3 = "მსწვლებწლი", typo4="მსწვლბწლი", correct = "მასწავლებელი";
        String typo1_1 = "მასწავლებული";

        assertEquals(3, levenshtein.editScore(word1,word2));

        assertEquals(correct, levenshtein.format(typo1,1));
        assertEquals(correct, levenshtein.format(typo1_1,2));
        assertEquals(typo2,   levenshtein.format(typo2,1));
        assertEquals(correct, levenshtein.format(typo2,2));
        assertEquals(typo3,   levenshtein.format(typo3,1));
        assertEquals(correct, levenshtein.format(typo3,3));
        assertEquals(correct, levenshtein.format(typo4));
    }
}
