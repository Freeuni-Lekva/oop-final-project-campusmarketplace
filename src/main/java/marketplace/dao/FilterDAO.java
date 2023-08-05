package marketplace.dao;

import marketplace.dao.daoInterfaces.FilterDAOInterface;
import marketplace.objects.FeedPost;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilterDAO implements FilterDAOInterface {
    private final BasicDataSource dataSource;

    public FilterDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void addFilter(int post_id, String filter) {
        String sql = "insert into filters (post_id, filter_text) values (?,?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, post_id);
            stmt.setString(2, filter);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeAllFilters(int post_id) {
        String sql = "delete from filters where post_id = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, post_id);

            stmt.executeUpdate();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FeedPost> filterPosts(List<String> filter) {
        String sql="";
        if(filter.size()==1) {
             sql = "select post_id from filters where filter_text=?";
        }
        else{
             sql = "select post_id from filters where filter_text in (";
            for (int i=0; i<filter.size();i++){
                sql+= i==filter.size()-1? "?" : "?, ";
            }
        }
        ArrayList<FeedPost> posts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            for(int i=0; i<filter.size();i++)
            stmt.setString(i+1, filter.get(i));
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
