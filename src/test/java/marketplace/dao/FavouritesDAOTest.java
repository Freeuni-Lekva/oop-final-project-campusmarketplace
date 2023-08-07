package marketplace.dao;

import marketplace.dao.daoInterfaces.FavouritesDAOInterface;
import marketplace.objects.FeedPost;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavouritesDAOTest {

    private BasicDataSource dataSource;
    private FavouritesDAOInterface favouritesDAO;

    @BeforeEach
    public void setup() throws SQLException {
        // Set up the database connection
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test_final_project_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        // Initialize the DAO object with the database connection
        favouritesDAO = new FavouritesDAO(dataSource);

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
            stmt.executeUpdate("DELETE FROM favourites");
            stmt.executeUpdate("DELETE FROM posts");
             stmt.executeUpdate("DELETE FROM profiles");
        }
    }

    @Test
    public void testAddFavourite() throws SQLException {
        // Insert a record into the 'favourites' table using the DAO method
        favouritesDAO.addFavourite(3, 1);
        favouritesDAO.addFavourite(6,1);
        favouritesDAO.addFavourite(5, 2);
        favouritesDAO.addFavourite(1,2);

        // Verify that the 'favourites' table has one record
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM favourites where profile_id=1");
            rs.next();
            int count = rs.getInt(1);
            assertEquals(2, count);
            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM favourites where profile_id=2");
            rs1.next();
            int count1 = rs1.getInt(1);
            assertEquals(2, count);
        }
    }

    @Test
    public void testDeleteFavourite() throws SQLException {

        favouritesDAO.addFavourite(3, 1);
        favouritesDAO.addFavourite(6,1);

        // Call the deleteFavourite method
        favouritesDAO.deleteFavourite(3, 1);

        // Verify that the 'favourites' table is empty
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM favourites where profile_id=1");
            rs.next();
            int count = rs.getInt(1);
            assertEquals(1, count);
            favouritesDAO.deleteFavourite(6, 1);
            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM favourites where profile_id=1");
            rs1.next();
            int count1 = rs1.getInt(1);
            assertEquals(0, count1);


        }
    }

    @Test
    public void testFavourites() throws SQLException {
        // Insert two records into the 'favourites' table
        favouritesDAO.addFavourite(3, 1);
        favouritesDAO.addFavourite(6,1);

        // Call the favourites method
        List<FeedPost> result = favouritesDAO.favourites(1);

        // Verify the number of returned favourites
        assertEquals(2, result.size());
        assertEquals(result.get(0).getTitle(),"mesame");
        assertEquals(result.get(1).getTitle(),"meeqvse");
    }
    @Test
    public void testAddDuplicateFavorite() throws SQLException {
        // Insert a favorite multiple times for a profile
        favouritesDAO.addFavourite(1, 1);
        favouritesDAO.addFavourite(1, 1);
        favouritesDAO.addFavourite(1, 1);

        // Verify that only one record exists in the 'favourites' table
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM favourites WHERE profile_id = 1 AND post_id = 1");
            rs.next();
            int count = rs.getInt(1);
            assertEquals(1, count);
        }
    }

    @Test
    public void testDeleteNonexistentFavorite() throws SQLException {
        // Call the deleteFavourite method for a favorite that doesn't exist
        favouritesDAO.deleteFavourite(1, 1);

        // Verify that the 'favourites' table remains unchanged
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM favourites");
            rs.next();
            int count = rs.getInt(1);
            assertEquals(0, count);
        }
    }

    @Test
    public void testRetrieveFavoritesForNonexistentProfile() throws SQLException {
        // Call the favourites method for a profile that doesn't exist
        List<FeedPost> result = favouritesDAO.favourites(10);

        // Verify that an empty list is returned
        assertEquals(0, result.size());
    }

    @Test
    public void testRetrieveFavoritesWithEmptyDatabase() throws SQLException {
        // Clear the 'favourites' table
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM favourites");
        }

        // Call the favourites method
        List<FeedPost> result = favouritesDAO.favourites(1);

        // Verify that an empty list is returned
        assertEquals(0, result.size());
    }


}