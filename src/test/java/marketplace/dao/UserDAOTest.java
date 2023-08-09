package marketplace.dao;

import marketplace.dao.UserDAO;
import marketplace.dao.daoInterfaces.UserDAOInterface;
import marketplace.objects.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private static BasicDataSource dataSource;
    private static UserDAOInterface userDAO;
    String firstName ;
    String surname ;
    String phoneNumber ;
    String email ;
    String passwordHash ;
    LocalDate birthDate ;
    @BeforeAll
    public static void setup() {
        // Set up the database connection pool
        dataSource = new BasicDataSource();
        // Configure the data source with your database credentials
        dataSource.setUrl("jdbc:mysql://localhost/test_final_project_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        // Create an instance of the UserDAO
        userDAO = new UserDAO(dataSource);
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
       firstName = "John";
         surname = "Doe";
         phoneNumber = "123456789";
         email = "john.doe@example.com";
         passwordHash = "password";
         birthDate = LocalDate.of(1990, 1, 1);


    }
        // Clear the data in the profiles table before each test
    @AfterEach
    public void cleanUp() throws SQLException {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM profiles");}


    }

    @Test
    public void testAddUser() {

        userDAO.addUser(firstName, surname, phoneNumber, email, passwordHash, birthDate);

        // Verify that the user exists in the database
        assertTrue(userDAO.existsEmail(email));
        assertTrue(userDAO.getAllUserIds().size()!=0);
        assertTrue(userDAO.getUser(userDAO.getAllUserIds().get(0)).getFirstName().equals("John"));
    }

    @Test
    public void testGetUser() {
        // Test getting a user by user ID


        // Add a user to the database
        userDAO.addUser(firstName, surname, phoneNumber, email, passwordHash, birthDate);

        // Get the user by user ID
        User user = userDAO.getUser(userDAO.getAllUserIds().get(0));
        assertNull(userDAO.getUser(-1));
        // Verify that the retrieved user matches the added user
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(surname, user.getSurname());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(birthDate.toString(), user.getBirthDate().toString());
    }

    @Test
    public void testGetUserByEmail() {
        // Test getting a user by email


        // Add a user to the database
        userDAO.addUser(firstName, surname, phoneNumber, email, passwordHash, birthDate);

        // Get the user by email
        User user = userDAO.getUser(email);
        assertNull(userDAO.getUser(""));
        // Verify that the retrieved user matches the added user
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(surname, user.getSurname());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(email, user.getEmail());
        assertEquals(passwordHash, user.getPasswordHash());
        assertEquals(birthDate.toString(), user.getBirthDate().toString());
    }

    @Test
    public void testGetAllUserIds() {


        // Add some users to the database
        userDAO.addUser(firstName, surname, phoneNumber, email, passwordHash, birthDate);
        userDAO.addUser("Jane", "Smith", "987654321", "jane.smith@example.com", "password", LocalDate.of(1995, 2, 2));

        // Get all user IDs
        List<Integer> userIds = userDAO.getAllUserIds();

        // Verify that the retrieved user IDs are correct
        assertEquals(2, userIds.size());
        assertTrue(userDAO.getUser(userDAO.getAllUserIds().get(0)).getFirstName().equals("John"));
        assertTrue(userDAO.getUser(userDAO.getAllUserIds().get(1)).getFirstName().equals("Jane"));    }
}