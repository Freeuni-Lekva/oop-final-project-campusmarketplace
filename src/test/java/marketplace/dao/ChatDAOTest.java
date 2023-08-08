package marketplace.dao;


import marketplace.objects.Message;
import marketplace.objects.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatDAOTest {

    private static BasicDataSource dataSource;
    private static ChatDAO chatDAO;
    private static UserDAO userDAO;

    @BeforeAll
    public static void setupDataSource() {
        // Set up the test database connection
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/test_final_project_db");
        dataSource.setUsername("root");
        dataSource.setPassword("password");

        // Create instances of the DAOs
        userDAO = new UserDAO(dataSource);
        chatDAO = new ChatDAO(dataSource, userDAO);
    }

    @BeforeEach
    public void setupTestData() {
        // Insert test data into the test database before each test
        try (Connection connection = dataSource.getConnection()) {
            // Insert test users
            String insertUserSql = "INSERT INTO profiles (profile_id, first_name, surname, phone_number, email, password_hash, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSql);
            insertUserStmt.setInt(1, 1);
            insertUserStmt.setString(2, "John");
            insertUserStmt.setString(3, "Doe");
            insertUserStmt.setString(4, "1234567890");
            insertUserStmt.setString(5, "john@example.com");
            insertUserStmt.setString(6, "password_hash");
            insertUserStmt.setDate(7, Date.valueOf("1990-01-01"));
            insertUserStmt.execute();

            insertUserStmt.setInt(1, 2);
            insertUserStmt.setString(2, "Jane");
            insertUserStmt.setString(3, "Smith");
            insertUserStmt.setString(4, "9876543210");
            insertUserStmt.setString(5, "jane@example.com");
            insertUserStmt.setString(6, "password_hash");
            insertUserStmt.setDate(7, Date.valueOf("1995-01-01"));
            insertUserStmt.execute();

            // Insert test chat messages
            String insertMessageSql = "INSERT INTO chats (message_id, sender_id, receiver_id, message, send_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertMessageStmt = connection.prepareStatement(insertMessageSql);
            insertMessageStmt.setInt(1, 1);
            insertMessageStmt.setInt(2, 1);
            insertMessageStmt.setInt(3, 2);
            insertMessageStmt.setString(4, "Hello Jane!");
            insertMessageStmt.setTimestamp(5, Timestamp.valueOf("2023-01-01 10:00:00"));
            insertMessageStmt.execute();

            insertMessageStmt.setInt(1, 2);
            insertMessageStmt.setInt(2, 2);
            insertMessageStmt.setInt(3, 1);
            insertMessageStmt.setString(4, "Hi John!");
            insertMessageStmt.setTimestamp(5, Timestamp.valueOf("2023-01-01 10:05:00"));
            insertMessageStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void cleanupTestData() {
        // Delete the test data from the test database after each test
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.execute("DELETE FROM chats");
            stmt.execute("DELETE FROM profiles");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMessagesForUsers() {
        int senderId = 1;
        int receiverId = 2;

        List<Message> messages = chatDAO.getMessagesForUsers(senderId, receiverId);

        assertEquals(2, messages.size());
        assertEquals("Hello Jane!", messages.get(1).getContent());
        assertEquals("Hi John!", messages.get(0).getContent());
    }

    @Test
    public void testLastMessages() {
        int userId = 1;
        int n = 1;

        List<User> users = chatDAO.lastMessages(userId, n);

        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals(2, user.getProfileId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getSurname());
    }

    @Test
    public void testSaveMessage() {
        Message message = new Message(3, 1, 2, "Test message", Timestamp.valueOf("2023-01-01 12:00:00"));

        chatDAO.saveMessage(message);

        List<Message> messages = chatDAO.getMessagesForUsers(1,2);
        assertEquals(3, messages.size());
        assertEquals("Test message", messages.get(0).getContent()); // Swap expected and actual values
        assertEquals(Timestamp.valueOf("2023-01-01 12:00:00"), messages.get(0).getSendTime());
    }
    @Test
    public void testGetMessagesForUsers_NonExistingUsers() {
        int senderId = 100;
        int receiverId = 200;

        List<Message> messages = chatDAO.getMessagesForUsers(senderId, receiverId);

        assertEquals(0, messages.size());
    }
    @Test
    public void testGetMessagesForUsers_OneNonExistingUser() {
        int senderId = 1;
        int receiverId = 100;

        List<Message> messages = chatDAO.getMessagesForUsers(senderId, receiverId);

        assertEquals(0, messages.size());
    }
    @Test
    public void testLastMessages_InvalidUser() {
        int userId = 100;
        int n = 1;

        List<User> users = chatDAO.lastMessages(userId, n);

        assertEquals(0, users.size());
    }
    @Test
    public void testSaveMessage_NullMessage() {
        Message message = null;

        chatDAO.saveMessage(message);

        List<Message> messages = chatDAO.getMessagesForUsers(1, 2);

        assertEquals(2, messages.size());
        assertEquals("Hello Jane!", messages.get(1).getContent());
        assertEquals("Hi John!", messages.get(0).getContent());
    }
    @AfterAll
    public static void closeDataSource() {
        // Close the data source after all tests are finished
        try {
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}