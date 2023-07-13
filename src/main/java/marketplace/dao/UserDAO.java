package marketplace.dao;

import marketplace.objects.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

public class UserDAO {
    private final BasicDataSource dataSource;

    public UserDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addUser(String firstName, String surname, String passwordHash, String email, String phoneNumber, LocalDate birthDate) {

        String sql = "INSERT INTO profiles (first_name, surname, phone_number,email,password_hash,birth_date) VALUES (?, ?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, surname);
            statement.setString(3, phoneNumber);
            statement.setString(4, email);
            statement.setString(5, phoneNumber);
            statement.setDate(6, Date.valueOf(birthDate));
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public boolean existsEmail(String email) {
        String sql = "select count(*) from profiles where email=? ";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getInt(1)>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(String email) {
        String sql = "select * from profiles where email = ?";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,email);

            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                int profileId = rs.getInt("profile_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("surname");
                java.util.Date birthday=rs.getDate("birth_date");
                String number = rs.getString("phone_number");
                String pass=rs.getString("password_hash");
                User user = new User(profileId,firstName,lastName,number,email,pass,birthday);
                return user;
            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
