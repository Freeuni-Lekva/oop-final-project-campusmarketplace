package marketplace.dao;

import marketplace.dao.daoInterfaces.ChatDAOInterface;
import marketplace.objects.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import marketplace.objects.User;
import org.apache.commons.dbcp2.BasicDataSource;


public class ChatDAO implements ChatDAOInterface {
    private final BasicDataSource dataSource;
    private UserDAO userdao;

    public ChatDAO(BasicDataSource dataSource, UserDAO userdao) {
        this.dataSource = dataSource;
        this.userdao = userdao;

    }

    @Override
    public List<Message> getMessagesForUsers(int senderId, int receiverId) {
        List<Message> messages = new ArrayList<>();
        String sql = "select * from chats where (sender_id=? and receiver_id=?) or (sender_id=? and receiver_id=?) order by send_time desc";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            statement.setInt(3, receiverId);
            statement.setInt(4, senderId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                String content = rs.getString("message");
                Timestamp sendtime = rs.getTimestamp("send_time");

                Message msg = new Message(messageId, senderId, receiverId, content, sendtime);
                messages.add(msg);
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return messages;

    }

    @Override
    public List<User> lastMessages(int userId, int n) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, max_time\n" +
                "FROM (\n" +
                "    SELECT id, MAX(send_time) AS max_time\n" +
                "    FROM (\n" +
                "        SELECT sender_id AS id, send_time\n" +
                "        FROM chats\n" +
                "        WHERE receiver_id = ?\n" +
                "        UNION ALL\n" +
                "        SELECT receiver_id AS id, send_time\n" +
                "        FROM chats\n" +
                "        WHERE sender_id = ?\n" +
                "    ) AS subquery\n" +
                "    GROUP BY id\n" +
                ") AS result\n" +
                "ORDER BY max_time DESC\n" +
                "LIMIT ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, n);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int prof_id = rs.getInt("id");
                users.add(userdao.getUser(prof_id));
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return users;
    }


    @Override
    public void saveMessage(Message message) {
        if (message == null) return;
        String sql = "insert into chats (sender_id, receiver_id,message,send_time) values (?,?,?,?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, message.getFromId());
            stmt.setInt(2, message.getToId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, message.getSendTime());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
