package marketplace.search;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/** This class is Formatter and its main purpose is to generate lemmas for words.
 *  It will help LSA to learn more synonyms, because noise caused with same words, but still different
 *  for LSA, will be reduced.
 *  For instance: ბილეთები and ბილეთი is the same, so lemma for both words are equal and it is ბილეთ-ი
 * */
public class Lemmatization extends Formatter{
    /** This is an api url, which is used to generate lemmas for words */
    private final String url = "https://qartnlp.iliauni.edu.ge/api/lemma";
    /** Stores word->lemma */
    private final Map<String, String> word_to_format;
    /** Stores lemmas */
    private final Set<String> formated_words;

    /** name of a file, which stores word_to_format map */
    private final String FILE_NAME = "map.properties";
    public Lemmatization(Set<String> words) {
        word_to_format = generate_or_read_map(words, FILE_NAME);
        formated_words = new TreeSet<>();
        formated_words.addAll(word_to_format.values());
    }

    /** If file_name file doesn't exist, it generates one, otherwise read from it
        Sending request to API is slow, so that faster the process
     * */
    private Map<String, String> generate_or_read_map(Set<String> words, String file_name){
        Map<String, String> map = new TreeMap<>();
        Properties properties = new Properties();
        final Path path = Paths.get(file_name);
        try {
            properties.load(Files.newInputStream(path));
            for (String key : properties.stringPropertyNames()) {
                map.put(key, properties.get(key).toString());
            }
            System.out.println("Lemma File Do Exists!");
        } catch (IOException e) {
            System.out.println("Lemma File Do NOT Exists!");
            for(String word : words){
                map.put(word, format(word));
            }
            properties.putAll(map);
            try {
                properties.store(Files.newOutputStream(path), null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return map;
    }

    /** Main method to format word. It also cleans word using cleanWord method */
    @Override
    public String format(String word) {
        word = cleanWord(word);
        if(word_to_format != null && word_to_format.containsKey(word))
            return word_to_format.get(word);
        if(formated_words != null && formated_words.contains(word))
            return word;
        String ans = "";
        try {
            URL u = new URI(url).toURL();
            HttpURLConnection con = (HttpURLConnection)u.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            String key = "\"text\":" + "\"" +word+"\"";
            String jsonInputString = "{"+key+"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            Gson gson = new GsonBuilder().create();
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();

                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JsonElement e = JsonParser.parseString(response.toString());
                ans = e.toString().substring(1,e.toString().length()-1);
            }
            con.disconnect();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        ans = take_key(ans, "lemma");
        ans = cleanWord(ans);
        if(ans.equals("")) return word;
        return ans;
    }

    /** This method lower cases word; trims it; removes letters, that are repeated more than 2 times
     * and removes chars that are not alphabetical, nor digits  */
    private String cleanWord(String word) {
        word = word.trim().toLowerCase();
        StringBuilder ans = new StringBuilder();

        Character prev = null;
        int repeat_counter = 0;
        for(int i = 0; i<word.length(); i++){
            char c = word.charAt(i);
            if(prev==null) prev = c;
            else if(prev == c) repeat_counter ++;
            else repeat_counter = 0;

            if((Character.isDigit(c) || Character.isLetter(c) || c==' ') && repeat_counter <=2)
                ans.append(c);
        }
        return ans.toString();
    }


    /** Takes json as a string and returns key's value */
    private String take_key(String json, String key){
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get(key).toString();
    }
}