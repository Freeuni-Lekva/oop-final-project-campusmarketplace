package marketplace.dao;

import marketplace.dao.daoInterfaces.UserDAOInterface;
import marketplace.objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserDAOInterface {
    private final BasicDataSource dataSource;

    public UserDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void addUser(String firstName, String surname, String phoneNumber, String email, String passwordHash, LocalDate birthDate) {
        String sql = "INSERT INTO profiles (first_name, surname, phone_number, email, password_hash, birth_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, surname);
            statement.setString(3, phoneNumber);
            statement.setString(4, email);
            statement.setString(5, passwordHash);
            statement.setDate(6, Date.valueOf(birthDate));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsEmail(String email) {
        String sql = "SELECT COUNT(*) FROM profiles WHERE email=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int userId) {
        String sql = "SELECT * FROM profiles WHERE profile_id = ?";
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String email = rs.getString("email");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("surname");
                java.util.Date birthday = rs.getDate("birth_date");
                String number = rs.getString("phone_number");
                String pass = rs.getString("password_hash");
                user = new User(userId, firstName, lastName, number, email, pass, birthday);
            }
            statement.close();
            rs.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(String email) {
        String sql = "SELECT * FROM profiles WHERE email = ?";
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int profileId = rs.getInt("profile_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("surname");
                java.util.Date birthday = rs.getDate("birth_date");
                String number = rs.getString("phone_number");
                String pass = rs.getString("password_hash");
                user = new User(profileId, firstName, lastName, number, email, pass, birthday);
            }
            rs.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getAllUserIds() {
        List<Integer> userIds = new ArrayList<>();
        String sql = "SELECT profile_id FROM profiles";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int profileId = rs.getInt("profile_id");
                userIds.add(profileId);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userIds;
    }
}