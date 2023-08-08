package marketplace.dao;

import marketplace.constants.PageConstants;
import marketplace.objects.FeedPost;
import marketplace.objects.Post;
import marketplace.objects.Photo;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostDAOTest {
    private BasicDataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private PostDAO postDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        dataSource = mock(BasicDataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        postDAO = new PostDAO(dataSource);
    }

    @Test
    public void testGetAllFeedPosts() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result
        when(resultSet.getInt("post_id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("test");
        when(resultSet.getDouble("price")).thenReturn(100.0);
        when(resultSet.getString("main_photo")).thenReturn("test_url");
        when(resultSet.getDate("publish_date")).thenReturn(new Date(System.currentTimeMillis()));

        List<FeedPost> posts = postDAO.getAllFeedPosts(0);
        assertEquals(1, posts.size());
        assertEquals("test", posts.get(0).getTitle());
    }
    @Test
    public void testGetPostById() throws SQLException {
        // Arrange
        int postId = 1;
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("profile_id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("Test Post");
        when(resultSet.getDouble("price")).thenReturn(9.99);
        when(resultSet.getString("description")).thenReturn("Test description");
        when(resultSet.getDate("publish_date")).thenReturn(new Date(System.currentTimeMillis()));

        // Act
        Post post = postDAO.getPostById(postId);

        // Assert
        assertNotNull(post);
        assertEquals(1, post.getProfile_id());
        assertEquals("Test Post", post.getTitle());
        assertEquals(9.99, post.getPrice());
        assertEquals("Test description", post.getDescription());
        assertNotNull(post.getDate());
    }

    @Test
    public void testAddNewPost() throws SQLException {
        // Arrange
        Post post = new Post(1, 1, "Test Post", 9.99, "Test description", new Date(System.currentTimeMillis()));
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);

        ResultSet rs = mock(ResultSet.class);
        when(stmt.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString(1)).thenReturn("1");

        // Act
        int postId = postDAO.addNewPost(post);

        // Assert
        assertEquals(1, postId);
    }
    @Test
    public void testDeletePost() throws SQLException {
        // Arrange
        int postId = 1;
        ResultSet rs = mock(ResultSet.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true); // Set up the mock ResultSet to return true

        // Act
        boolean result = postDAO.deletePost(postId);

        // Assert
        assertTrue(result);
        verify(stmt, times(4)).setInt(eq(1), eq(postId));
        verify(stmt, times(4)).executeUpdate();
        verify(stmt, times(5)).close(); // Adjust the verification to 4 times
        verify(rs, times(1)).close(); // Adjust the verification to 1 time
    }
    @Test
    public void testGetAllUserPosts() throws SQLException {
        // Arrange
        int userId = 1;
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result
        when(resultSet.getInt("post_id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("Test Post");
        when(resultSet.getDouble("price")).thenReturn(9.99);
        when(resultSet.getString("main_photo")).thenReturn("photo_url");
        when(resultSet.getDate("publish_date")).thenReturn(new Date(System.currentTimeMillis()));

        // Act
        List<FeedPost> userPosts = postDAO.getAllUserPosts(userId);

        // Assert
        assertEquals(1, userPosts.size());
        FeedPost userPost = userPosts.get(0);
        assertEquals(1, userPost.getPost_id());
        assertEquals("Test Post", userPost.getTitle());
        assertEquals(9.99, userPost.getPrice());
        assertEquals("photo_url", userPost.getPhoto_address());
    }
    @Test
    public void testAddMainPhoto() throws SQLException {
        // Arrange
        int postId = 1;
        String photoUrl = "https://example.com/photo.jpg";
        Connection connection = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(stmt);
        doNothing().when(stmt).setString(anyInt(), anyString());
        doNothing().when(stmt).setInt(anyInt(), anyInt());
        when(stmt.executeUpdate()).thenReturn(1);

        // Act
        postDAO.addMainPhoto(postId, photoUrl);

        // Assert
        verify(stmt).setString(1, photoUrl);
        verify(stmt).setInt(2, postId);
        verify(stmt).executeUpdate();
        verify(stmt).close();
    }

    @Test
    public void testGetMainPhoto() throws SQLException {
        // Arrange
        int postId = 1;
        String photoUrl = "https://example.com/photo.jpg";
        int photoId = 1;
        Connection connection = mock(Connection.class);
        PreparedStatement stmt1 = mock(PreparedStatement.class);
        PreparedStatement stmt2 = mock(PreparedStatement.class);
        ResultSet rs1 = mock(ResultSet.class);
        ResultSet rs2 = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(stmt1, stmt2);
        doNothing().when(stmt1).setInt(anyInt(), anyInt());
        doNothing().when(stmt2).setString(anyInt(), anyString());
        when(stmt1.executeQuery()).thenReturn(rs1);
        when(stmt2.executeQuery()).thenReturn(rs2);
        when(rs1.next()).thenReturn(true);
        when(rs1.getString(1)).thenReturn(photoUrl);
        when(rs2.next()).thenReturn(true);
        when(rs2.getInt(1)).thenReturn(photoId);

        // Act
        Photo photo = postDAO.getMainPhoto(postId);

        // Assert
        assertNotNull(photo);
        assertEquals(photoId, photo.getPhoto_id());
        assertEquals(photoUrl, photo.getPhoto_url());
        verify(stmt1).setInt(1, postId);
        verify(stmt2).setString(1, photoUrl);
        verify(stmt1).executeQuery();
        verify(stmt2).executeQuery();
        verify(rs1).close();
        verify(rs2).close();
    }

    @Test
    public void testUpdatePost() throws SQLException {
        // Arrange
        int postId = 1;
        int profileId = 2;
        String title = "New Title";
        double price = 100.0;
        String description = "New Description";
        Date date = new Date(System.currentTimeMillis()); // Provide the appropriate Date object
        Post post = new Post(profileId, postId, title, price, description, date);

        Connection connection = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(stmt);
        doNothing().when(stmt).setString(anyInt(), anyString());
        doNothing().when(stmt).setDouble(anyInt(), anyDouble());
        doNothing().when(stmt).setLong(anyInt(), anyLong());
        when(stmt.executeUpdate()).thenReturn(1);

        // Act
        postDAO.updatePost(postId, post);

        // Assert
        verify(stmt).setString(1, post.getTitle());
        verify(stmt).setString(2, post.getDescription());
        verify(stmt).setDouble(3, post.getPrice());
        verify(stmt).setLong(4, postId);
        verify(stmt).executeUpdate();
        verify(stmt).close();
    }
}
