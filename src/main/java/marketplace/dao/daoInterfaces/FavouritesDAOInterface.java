package marketplace.dao.daoInterfaces;

import marketplace.objects.FeedPost;

import java.util.List;

public interface FavouritesDAOInterface {
    public void addFavourite(int post_id, int profile_id);
    public boolean isFavourite(int post_id, int profile_id);

    public void deleteFavourite(int post_id, int profile_id);

    public List<FeedPost> favourites(int profile_id);
}
