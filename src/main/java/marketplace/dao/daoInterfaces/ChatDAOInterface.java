package marketplace.dao.daoInterfaces;

import marketplace.objects.Message;
import marketplace.objects.User;

import java.util.List;

public interface ChatDAOInterface {
    public List<Message> getMessagesForUsers(int senderId, int receiverId);
    public List<User> lastMessages(int userId, int n);
    public void saveMessage(Message message) ;
}
