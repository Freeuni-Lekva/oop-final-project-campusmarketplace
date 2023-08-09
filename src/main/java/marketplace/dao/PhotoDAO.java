package marketplace.dao;

import marketplace.dao.daoInterfaces.PhotoDAOInterface;
import marketplace.objects.Photo;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;

public class PhotoDAO implements PhotoDAOInterface {
    private final BasicDataSource dataSource;

    public PhotoDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
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

    @Override
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

    @Override
    public void addPhoto(int post_id, String photo_url) {
        String sql = "INSERT INTO photos (post_id, photo_url) VALUES (?,?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, post_id);
            stmt.setString(2, photo_url);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
