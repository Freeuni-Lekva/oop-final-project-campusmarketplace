package marketplace.dao;

import marketplace.objects.Message;

import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;


public class ChatDAO {
    private final BasicDataSource dataSource;

    public ChatDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Message> getMessagesForUsers(int senderId, int receiverId) {
        return null;
    }

    public void saveMessage(Message message) {

    }
}
