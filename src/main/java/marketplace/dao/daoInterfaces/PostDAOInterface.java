package marketplace.dao.daoInterfaces;

import marketplace.objects.FeedPost;
import marketplace.objects.Photo;
import marketplace.objects.Post;

import java.util.ArrayList;
import java.util.List;

public interface PostDAOInterface{
    ArrayList<FeedPost> getAllFeedPosts(int page);
    Post getPostById(int post_id);
    int addNewPost(Post post);
    boolean deletePost(int post_id);
    List<FeedPost> getAllUserPosts(int userId);
    void addMainPhoto(int post_id, String photo_url);
    Photo getMainPhoto(int post_id);

    void updatePost(int post_id, Post post);

}

