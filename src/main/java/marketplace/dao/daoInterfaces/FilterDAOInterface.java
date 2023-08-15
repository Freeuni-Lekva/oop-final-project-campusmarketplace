package marketplace.dao.daoInterfaces;

import marketplace.objects.FeedPost;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public interface FilterDAOInterface {
    Map<Integer, String> FILTERS = Map.ofEntries(
            entry(0, "test"),
            entry(1, "education"),
            entry(2, "tech"),
            entry(3, "furniture"),
            entry(4, "home"),
            entry(5, "animals"),
            entry(6, "music"),
            entry(7, "entertainment"),
            entry(8, "books")
    );
    void addFilter(int post_id, String filter);
    void removeAllFilters(int post_id);

    List<FeedPost> filterPosts(List<String> filter);
}
