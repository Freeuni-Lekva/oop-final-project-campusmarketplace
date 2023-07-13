package marketplace.dao;

import marketplace.objects.FeedPost;
import marketplace.objects.Photo;
import marketplace.objects.Post;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDAO {
    private final BasicDataSource dataSource;

    public PostDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<FeedPost> getAllFeedPosts() {

        ArrayList<FeedPost> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM posts");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String post_id=Integer.toString(rs.getInt("post_id"));
                String title = rs.getString("title");
                Double price = rs.getDouble("price");
                String photo_address=rs.getString("main_photo");
                Date date = rs.getDate("publish_date");


               FeedPost post = new FeedPost(  post_id,  photo_address,  title,  price,   date);
                posts.add(post);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;

    }

    public Post getPostById(String post_id) {
        Post post = null;
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM posts WHERE post_id = ? ");
            stmt.setString(1, post_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String profile_id =Integer.toString( rs.getInt("profile_id"));
                String title = rs.getString("title");
                Double price = rs.getDouble("price");
                 String description=rs.getString("description");
                 Date date = rs.getDate("publish_date");



                post = new Post( profile_id,  post_id,  title,  price,  description,date);

            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    public String addNewPost(Post post) {
        String sql = "INSERT INTO posts (profile_id,title, price, description, publish_date) VALUES (?,?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(post.getProfile_id()));

            stmt.setString(2, post.getTitle());
            stmt.setDouble(3, post.getPrice());
            stmt.setString(4, post.getDescription());
            stmt.setDate(5,post.getDate());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            stmt.close();
            if (rs.next()) {
                String postId = rs.getString(1);
                return postId;
            }        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletePost(String post_id) {
        try (Connection conn = dataSource.getConnection()) {
            Post post = getPostById(post_id);
            if(post==null) return;
            PreparedStatement stmt2 = conn.prepareStatement("delete from filters where post_id = ?");
            stmt2.setLong(1, Integer.parseInt(post_id));
            stmt2.executeUpdate();

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM posts WHERE post_id = ? ");
            stmt.setInt(1, Integer.parseInt(post_id));
            stmt.executeUpdate();
            stmt.close();
            stmt2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Photo> getPhotos(String post_id) {
        return null;
    }

    public Photo getMainPhoto(String post_id){
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select main_photo from posts where post_id=?");
            stmt.setInt(1,Integer.parseInt(post_id));
            ResultSet rs= stmt.executeQuery();
            String photoUrl=rs.getString(1);
            rs.close();
            PreparedStatement stmt2 = conn.prepareStatement("select photo_id from photos where photo_url=?");
            stmt2.setString(1,photoUrl);
            ResultSet rs2 = stmt2.executeQuery();


            String photoId=Integer.toString(rs2.getInt(1));
            Photo photo = new Photo(photoId,photoUrl);
            return photo;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deletePhoto(String photo_id) {

    }

    public void addPhoto(String post_id, String photo_url) {

    }
}