package marketplace.dao.daoInterfaces;

import marketplace.objects.Message;

import java.util.List;

public interface ChatDAOInterface {
    public List<Message> getMessagesForUsers(int senderId, int receiverId);

    public void saveMessage(Message message) ;
}
