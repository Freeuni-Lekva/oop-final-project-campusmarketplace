package marketplace.search;

import java.util.Map;
import java.util.Set;

import static java.lang.Math.min;
import static java.util.Map.entry;

public class Levenshtein extends Formatter{

    private final int LIMIT;
    private final Set<String> words;
    /** Maps keys that appear close to each other on keyboard */
    private final Map<String, String> mapKeys = Map.ofEntries(
            entry("ა", "ქწჭსშზძ"),
            entry("ბ", "ვგჰნ"),
            entry("ც", "ხდფვ"),
            entry("ჩ", "ხდფვ"),
            entry("დ", "სშერღფცჩხ"),
            entry("ე", "წჭსშდრღ"),
            entry("ფ", "დრღტთგვცჩ"),
            entry("გ", "ფტთყჰბვ"),
            entry("ჰ", "გყუჯჟნბ"),
            entry("ი", "უჯჟკო"),
            entry("ჯ", "ჰუიკმნ"),
            entry("ჟ", "ჰუიკმნ"),
            entry("კ", "ჯჟიოლმ"),
            entry("ლ", "პოკ"),
            entry("მ", "ნჯჟკ"),
            entry("ნ", "ბჰჯჟმ"),
            entry("ო", "იკლპ"),
            entry("პ", "ოლ"),
            entry("ქ", "წჭსშა"),
            entry("რ", "ედფტთ"),
            entry("ღ", "ედფტთ"),
            entry("ს", "წჭედხზძა"),
            entry("შ", "წჭედხზძა"),
            entry("ტ", "რღფგყ"),
            entry("თ", "რღფგყ"),
            entry("უ", "ყჰჯჟი"),
            entry("ვ", "ცჩფგბ"),
            entry("წ", "ქასშე"),
            entry("ჭ", "ქასშე"),
            entry("ხ", "ზძსშდცჩ"),
            entry("ყ", "ტთგჰუ"),
            entry("ზ", "ასშხ"),
            entry("ძ", "ასშხ")
            );

    public Levenshtein(Set<String> words, int limit){
        LIMIT = limit;
        this.words = words;
    }

    public int editScore(String w1, String w2){
        int[][] dp = new int[w1.length() + 1][w2.length() + 1];
        for (int i = 0; i <= w1.length(); i++) {
            for (int j = 0; j <= w2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    int sub_cost = 0;
                    // If on keyboard (only for KA letters) chars appear far to each other, then higher cost it has
                    if(w1.charAt(i-1) != w2.charAt(j-1)) {
                        sub_cost = 1;
                        if((w1.charAt(i-1) >= 'ა' && w1.charAt(i-1) <= 'ჰ') && (w2.charAt(j-1) >= 'ა' && w2.charAt(j-1) <= 'ჰ')){
                            String w1_str = Character.toString(w1.charAt(i - 1));
                            String w2_str = Character.toString(w2.charAt(j-1));
                            if(mapKeys.containsKey(w1_str)){
                                String val = mapKeys.get(w1_str);
                                if(!val.contains(w2_str)) sub_cost = 2;
                            }
                        }
                    }
                    dp[i][j] = min(dp[i - 1][j - 1] + sub_cost, min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[w1.length()][w2.length()];
    }

    @Override
    public String format(String word) {
        return format(word, LIMIT);
    }

    public String format(String word, int limit){
        if(words.contains(word)) return word;
        String ans = word;
        int edit_distance = Integer.MAX_VALUE;
        for(String w : words){
            int cur_score = editScore(word, w);
            if (edit_distance > cur_score) {
                edit_distance = cur_score;
                ans = w;
            }
        }
        if(limit >= edit_distance)
            return ans;
        return word;
    }
}
