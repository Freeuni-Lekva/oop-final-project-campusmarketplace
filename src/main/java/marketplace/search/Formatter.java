package marketplace.search;

import java.util.*;
import java.util.stream.Collectors;

/** This is an abstract class, which will be used to preprocess data (such as posts and queries) */
public abstract class Formatter {

    /** Main method to format all Sentences */
    public String formatAll(String sentence){
        List<String> words = splitSentence(sentence);
        return joinSentence(words.stream().map(this::format).collect(Collectors.toList()));
    }
    public String format(String word){
        return null;
    }
    public List<String> splitSentence(String sentence){
        List<String> words = new ArrayList<>();
        Collections.addAll(words, sentence.split("\\s+"));
        return words;
    }
    public String joinSentence(List<String> words){
        StringBuilder sentence = new StringBuilder();
        for(String word : words){
            sentence.append(word).append(' ');
        }
        return sentence.toString().trim();
    }
}
