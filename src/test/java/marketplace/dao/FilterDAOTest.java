package marketplace.dao;

import marketplace.objects.FeedPost;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterDAOTest {

    private BasicDataSource dataSource;
    private FilterDAO filtersDAO;

    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test_final_project_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        // Initialize the DAO object with the database connection
        filtersDAO = new FilterDAO(dataSource);
        try (Connection conn = dataSource.getConnection()) {
            insertProfile(conn, 1, "John", "Doe", "1234567890", "john.doe@example.com", "password1");
            insertPost(conn, 1, 1,"pirveli",35,"pirveli posti");
            insertPost(conn, 1, 4,"meotxe",35,"meotxe posti");

            // Insert the second profile
            insertProfile(conn, 2, "Alice", "Smith", "9876543210", "alice.smith@example.com", "password2");
            insertPost(conn, 2, 2,"meore",38,"meore posti");
            insertPost(conn, 2, 5,"mexute",38,"mexute posti");

            // Insert the third profile
            insertProfile(conn, 3, "Bob", "Johnson", "5555555555", "bob.johnson@example.com", "password3");
            insertPost(conn, 3, 3,"mesame",45,"mesame posti");
            insertPost(conn, 3, 6,"meeqvse",45,"meeqvse posti");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void insertPost(Connection conn, int profileId, int postId, String title, double price,
                                   String description) throws SQLException {
        String sql = "INSERT INTO posts (profile_id, post_id, title, price, description, publish_date, main_photo) " +
                "VALUES (?, ?, ?, ?, ?, CURDATE(), 'images/default.png')";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setInt(2, postId);
            stmt.setString(3, title);
            stmt.setDouble(4, price);
            stmt.setString(5, description);
            stmt.executeUpdate();
        }
    }

    private static void insertProfile(Connection conn, int profileId, String firstName, String surname,
                                      String phoneNumber, String email, String password) throws SQLException {
        String sql = "INSERT INTO profiles (profile_id, first_name, surname, phone_number, email, password_hash) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, profileId);
            stmt.setString(2, firstName);
            stmt.setString(3, surname);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, email);
            stmt.setString(6, password);
            stmt.executeUpdate();
        }
    }
    @AfterEach
    public void cleanup() throws SQLException {
        // Clear the favourites and profiles tables after each test
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM filters");
            stmt.executeUpdate("DELETE FROM posts");
            stmt.executeUpdate("DELETE FROM profiles");
        }
    }

    @Test
    public void testAddFilter() throws SQLException {
        // Add a filter for a specific price range
        filtersDAO.addFilter(1, "30-40");

        // Verify that the filter exists in the database
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM filters WHERE filter_text = '30-40'");
            rs.next();
            int count = rs.getInt(1);
            assertEquals(1, count);
        }
    }

    @Test
    public void testRemoveAllFilters() throws SQLException {
        // Add multiple filters
        filtersDAO.addFilter(1, "30-40");
        filtersDAO.addFilter(1, "electronics");

        // Remove all filters
        filtersDAO.removeAllFilters(1);

        // Verify that the filters table is empty
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM filters where post_id=1");
            rs.next();
            int count = rs.getInt(1);
            assertEquals(0, count);
        }
    }

    @Test
    public void testFilterPosts() throws SQLException {
        // Add a filter for a specific price range
        filtersDAO.addFilter(1, "30-40");
        filtersDAO.addFilter(2,"30-40");
        filtersDAO.addFilter(3,"filtri");
        filtersDAO.addFilter(2,"filtri");
        filtersDAO.addFilter(4,"meorefiltri");
        filtersDAO.addFilter(5,"meorefiltri");
        filtersDAO.addFilter(6,"fil");

        // Filter the posts based on the added filter
        List<FeedPost> filteredPosts = filtersDAO.filterPosts(Arrays.asList("30-40","filtri","meorefiltri"));
        List<FeedPost> filteredPosts2 = filtersDAO.filterPosts(Arrays.asList("fil"));

        // Verify that the filtered posts are returned correctly
        assertEquals(5, filteredPosts.size());
        assertEquals("pirveli", filteredPosts.get(0).getTitle());
        assertEquals(1,filteredPosts2.size());
    }
}