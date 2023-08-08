package marketplace.dao;

import marketplace.objects.Photo;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PhotoDAOTest {
    private BasicDataSource dataSource;
    private Connection connection;
    private PhotoDAO dao;

    @BeforeEach
    public void setup() throws SQLException {
        dataSource = mock(BasicDataSource.class);
        connection = mock(Connection.class);
        dao = new PhotoDAO(dataSource);

        when(dataSource.getConnection()).thenReturn(connection);
    }

    @Test
    public void testGetPhotos() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("photo_id")).thenReturn(1);
        when(resultSet.getString("photo_url")).thenReturn("url");

        ArrayList<Photo> photos = dao.getPhotos(1);

        assertEquals(1, photos.size());
        assertEquals(1, photos.get(0).getPhoto_id());
        assertEquals("url", photos.get(0).getPhoto_url());

        verify(connection, times(1)).prepareStatement(any(String.class));
        verify(statement, times(1)).setLong(1, 1);
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
    }

    @Test
    public void testDeletePhoto() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(statement);

        dao.deletePhoto(1);

        verify(connection, times(1)).prepareStatement(any(String.class));
        verify(statement, times(1)).setInt(1, 1);
        verify(statement, times(1)).executeUpdate();
    }

    @Test
    public void testAddPhoto() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(any(String.class), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);

        dao.addPhoto(1, "url");

        verify(connection, times(1)).prepareStatement(any(String.class), eq(Statement.RETURN_GENERATED_KEYS));
        verify(statement, times(1)).setInt(1, 1);
        verify(statement, times(1)).setString(2, "url");
        verify(statement, times(1)).executeUpdate();
    }

    @Test
    public void testGetPhotos_NoPhotosFound() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // No photos found

        PhotoDAO photoDAO = new PhotoDAO(dataSource);

        ArrayList<Photo> photos = photoDAO.getPhotos(1);

        assertNotNull(photos);
        assertEquals(0, photos.size());

        verify(resultSet).close();
        verify(statement).close();
    }

    @Test
    public void testDeletePhoto_NonExistentID() throws SQLException {
        // Arrange
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(0);

        PhotoDAO photoDAO = new PhotoDAO(dataSource);

        photoDAO.deletePhoto(1);

        verify(statement).close();
    }

    @Test
    public void testAddPhoto_ReturnGeneratedID() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet generatedKeys = mock(ResultSet.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(generatedKeys);
        when(generatedKeys.next()).thenReturn(true);
        when(generatedKeys.getInt(1)).thenReturn(1);

        PhotoDAO photoDAO = new PhotoDAO(dataSource);

        photoDAO.addPhoto(1, "photo_url");

        verify(statement).close();
    }
}