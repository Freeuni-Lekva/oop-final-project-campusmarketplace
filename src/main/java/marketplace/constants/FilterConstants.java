package marketplace.constants;

import java.util.Map;

import static java.util.Map.entry;

public class FilterConstants {
    public static final int MAX_NUMBER_OF_FILTERS = 3;
    public static final String[] filters = {
            "tech",
            "furniture",
            "home",
            "music",
            "entertainment",
            "animals",
            "fashion",
            "education",
            "books"
    };

    public static final Map<Integer, String> FILTERS_MAP = Map.ofEntries(
            entry(0, "test"),
            entry(1, "education"),
            entry(2, "tech"),
            entry(3, "furniture"),
            entry(4, "home"),
            entry(5, "animals"),
            entry(6, "music"),
            entry(7, "entertainment"),
            entry(8, "books"),
            entry(9, "fashion")
    );

    public static final Map<String, Integer> REV_FILTERS_MAP = Map.ofEntries(
            entry("test", 0),
            entry( "education",1),
            entry( "tech",2),
            entry( "furniture", 3),
            entry( "home", 4),
            entry( "animals", 5),
            entry( "music", 6),
            entry( "entertainment", 7),
            entry( "books", 8),
            entry( "fashion", 9)
    );
}
