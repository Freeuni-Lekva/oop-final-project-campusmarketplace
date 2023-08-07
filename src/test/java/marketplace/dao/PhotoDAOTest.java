//package marketplace.dao;
//
//import marketplace.objects.Photo;
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.junit.jupiter.api.*;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class PhotoDAOTest {
//
//    private BasicDataSource dataSource;
//    private PhotoDAO photoDAO;
//
//    @BeforeEach
//    public void setup() throws SQLException {
//        // Set up the database connection
//        dataSource = new BasicDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost/test_final_project_db");
//        dataSource.setUsername("root");
//        dataSource.setPassword("password");
//
//        // Initialize the DAO object with the database connection
//        photoDAO = new PhotoDAO(dataSource);
//
//        try (Connection conn = dataSource.getConnection()) {
//            // Insert test data
//            insertPhoto(conn, 1, "photo1.jpg");
//            insertPhoto(conn, 1, "photo2.jpg");
//            insertPhoto(conn, 2, "photo3.jpg");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void insertPhoto(Connection conn, int postId, String photoUrl) throws SQLException {
//        String sql = "INSERT INTO photos (post_id, photo_url) VALUES (?, ?)";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, postId);
//            stmt.setString(2, photoUrl);
//            stmt.executeUpdate();
//        }
//    }
//
//    @AfterEach
//    public void cleanup() throws SQLException {
//        // Clear the photos table after each test
//        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate("DELETE FROM photos");
//        }
//    }
//
//    @Test
//    public void testGetPhotos() {
//        // Get photos for a specific post
//        ArrayList<Photo> photos = photoDAO.getPhotos(1);
//
//        // Verify that the correct number of photos is returned
//        assertEquals(2, photos.size());
//
//        // Verify the details of the first photo
//        assertEquals(1, photos.get(0).getPhoto_id());
//        assertEquals("photo1.jpg", photos.get(0).getPhoto_url());
//
//        // Verify the details of the second photo
//        assertEquals(2, photos.get(1).getPhoto_id());
//        assertEquals("photo2.jpg", photos.get(1).getPhoto_url());
//    }
//
//    @Test
//    public void testDeletePhoto() throws SQLException {
//        // Delete a photo
//        photoDAO.deletePhoto(1);
//
//        // Verify that the photo is deleted from the database
//        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM photos WHERE photo_id = 1");
//            rs.next();
//            int count = rs.getInt(1);
//            assertEquals(0, count);
//        }
//    }
//
//    @Test
//    public void testAddPhoto() throws SQLException {
//        // Add a new photo
//        photoDAO.addPhoto(2, "photo4.jpg");
//
//        // Verify that the photo is added to the database
//        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
//            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM photos WHERE photo_url = 'photo4.jpg'");
//            rs.next();
//            int count = rs.getInt(1);
//            assertEquals(1, count);
//        }
//    }
//}