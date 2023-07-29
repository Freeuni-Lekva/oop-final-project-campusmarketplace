package marketplace.chat;

import marketplace.config.ChatConfig;
import marketplace.dao.ChatDAO;
import marketplace.objects.Message;

import javax.servlet.ServletContext;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{senderId}/{receiverId}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class,
        configurator = ChatConfig.class)
public class ChatEndpoint {
    private static final Map<Integer, Session> sessions = new ConcurrentHashMap<>();
    private ChatDAO chatDAO;

    @OnOpen
    public void onOpen(Session session, @PathParam("senderId") int senderId, @PathParam("receiverId") int receiverId) throws IOException, EncodeException {
        sessions.put(senderId, session);


        ServletContext servletContext = ChatConfig.getServletContext();
        chatDAO = (ChatDAO) servletContext.getAttribute("chatDAO");

        // Load user's old messages from the chatDAO and send them
        for (Message message : chatDAO.getMessagesForUsers(senderId, receiverId)) {
            session.getBasicRemote().sendObject(message);
        }
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        chatDAO.saveMessage(message);

        // Send the message to the sender
        sessions.get(message.getFromId()).getBasicRemote().sendObject(message);

        // Send the message to the recipient
        Session recipientSession = sessions.get(message.getToId());
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.getBasicRemote().sendObject(message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
