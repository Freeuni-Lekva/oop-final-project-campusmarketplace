package marketplace.dao;

import marketplace.constants.PageConstants;
import marketplace.objects.FeedPost;
import marketplace.objects.Photo;
import marketplace.objects.Post;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class PostDAO {
    private final BasicDataSource dataSource;

    public PostDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<FeedPost> getAllFeedPosts(int page) {

        ArrayList<FeedPost> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM posts LIMIT ? OFFSET ?");
            stmt.setInt(1, PageConstants.PAGE_SIZE);
            stmt.setInt(2, PageConstants.PAGE_SIZE * page);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int post_id = rs.getInt("post_id");
                String title = rs.getString("title");
                double price = rs.getDouble("price");
                String photo_address = rs.getString("main_photo");
                Date date = rs.getDate("publish_date");
                FeedPost post = new FeedPost(post_id, photo_address, title, price, date);
                posts.add(post);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;

    }

    public Post getPostById(int post_id) {
        Post post = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM posts WHERE post_id = ? ");
            stmt.setString(1, Integer.toString(post_id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int profile_id = rs.getInt("profile_id");
                String title = rs.getString("title");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                Date date = rs.getDate("publish_date");


                post = new Post(profile_id, post_id, title, price, description, date);

            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public int addNewPost(Post post) {
        String sql = "INSERT INTO posts (profile_id,title, price, description, publish_date) VALUES (?,?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, post.getProfile_id());

            stmt.setString(2, post.getTitle());
            stmt.setDouble(3, post.getPrice());
            stmt.setString(4, post.getDescription());
            stmt.setDate(5, post.getDate());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                String postId = rs.getString(1);
                return Integer.parseInt(postId);
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deletePost(int post_id) {
        try (Connection conn = dataSource.getConnection()) {
            Post post = getPostById(post_id);
            if (post == null) return;
            PreparedStatement stmt2 = conn.prepareStatement("delete from filters where post_id = ?");
            stmt2.setLong(1, post_id);
            stmt2.executeUpdate();

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM posts WHERE post_id = ? ");
            stmt.setInt(1, post_id);
            stmt.executeUpdate();
            stmt.close();
            stmt2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Photo> getPhotos(int post_id) {
        ArrayList<Photo> photos = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM photos WHERE post_id = ?");
            statement.setLong(1, post_id);
            ResultSet rs = statement.executeQuery();
            photos = new ArrayList<>();
            while (rs.next()) {
                int photo_id = rs.getInt("photo_id");
                String photo_url = rs.getString("photo_url");
                photos.add(new Photo(photo_id, photo_url));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public void addMainPhoto(int post_id, String photo_url){
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE posts SET main_photo = ? WHERE post_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, photo_url);
            stmt.setInt(2, post_id);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (Exception e){ e.printStackTrace(); }
    }

    public Photo getMainPhoto(int post_id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select main_photo from posts where post_id=?");
            stmt.setInt(1, post_id);
            ResultSet rs = stmt.executeQuery();
            String photoUrl = rs.getString(1);
            rs.close();
            PreparedStatement stmt2 = conn.prepareStatement("select photo_id from photos where photo_url=?");
            stmt2.setString(1, photoUrl);
            ResultSet rs2 = stmt2.executeQuery();


            int photoId = rs2.getInt(1);
            Photo photo = new Photo(photoId, photoUrl);
            return photo;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletePhoto(int photo_id) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM photos WHERE photo_id = ? ");
            stmt.setInt(1, photo_id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPhoto(int post_id, String photo_url) {

    }
}