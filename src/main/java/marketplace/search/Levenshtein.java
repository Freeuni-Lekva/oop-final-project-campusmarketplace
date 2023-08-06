package marketplace.search;

import java.util.Set;

import static java.lang.Math.min;

public class Levenshtein extends Formatter{

    private final int LIMIT;
    private final Set<String> words;

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
                    if(w1.charAt(i-1) != w2.charAt(j-1)) sub_cost = 1;
                    dp[i][j] = min(dp[i - 1][j - 1] + sub_cost, min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[w1.length()][w2.length()];
    }

    @Override
    public String format(String word) {
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
        if(LIMIT >= edit_distance)
            return ans;
        return word;
    }
}
