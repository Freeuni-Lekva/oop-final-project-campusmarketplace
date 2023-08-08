package marketplace.dao.daoInterfaces;

import marketplace.objects.FeedPost;

import java.util.List;

public interface FilterDAOInterface {

    void addFilter(int post_id, String filter);
    void removeAllFilters(int post_id);

    List<FeedPost> filterPosts(List<String> filter);
}
