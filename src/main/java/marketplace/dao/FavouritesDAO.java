package marketplace.dao;

import marketplace.dao.daoInterfaces.FavouritesDAOInterface;
import marketplace.objects.FeedPost;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavouritesDAO implements FavouritesDAOInterface {
    private final BasicDataSource dataSource;

    public FavouritesDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void addFavourite(int post_id, int profile_id) {
       if(isFavourite(post_id,profile_id)) return;

        String sql = "insert into favourites (profile_id,post_id) values (?,?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, profile_id);
            stmt.setInt(2, post_id);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFavourite(int postId, int profileId) { String check = "select count(*) from favourites where profile_id = ? and post_id = ?";
        ArrayList<FeedPost> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(check);

            stmt.setInt(1, profileId);
            stmt.setInt(2,postId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                if(rs.getInt(1)!=0) return true;
            }


            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteFavourite(int post_id, int profile_id) {
        String sql =  "delete from favourites where profile_id = ? and post_id = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, profile_id);
            stmt.setInt(2,post_id);
            stmt.executeUpdate();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FeedPost> favourites(int profile_id) {
        String sql = "select * from posts where post_id in (select post_id from favourites where profile_id = ?)";

        ArrayList<FeedPost> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, profile_id);
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
}