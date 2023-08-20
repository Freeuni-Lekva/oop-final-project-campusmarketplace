package marketplace.dao.daoInterfaces;

import marketplace.objects.FeedPost;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public interface FilterDAOInterface {
    void addFilter(int post_id, String filter);
    void removeAllFilters(int post_id);

    List<FeedPost> filterPosts(List<String> filter);
}
