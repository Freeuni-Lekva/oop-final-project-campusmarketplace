package marketplace.servlets;

import marketplace.annotation.Secure;
import marketplace.dao.ChatDAO;
import marketplace.dao.UserDAO;
import marketplace.objects.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static marketplace.constants.ChatConstants.LAST_N_MESSAGED_USER;

@Secure
@WebServlet(name = "chat", value = "/chat")
public class ChatServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChatDAO chatDAO = (ChatDAO) request.getServletContext().getAttribute("chatDAO");
        User user = (User) request.getSession().getAttribute("user");
        List<User> lastMessagedUsers = chatDAO.lastMessages(user.getProfileId(), LAST_N_MESSAGED_USER);

        request.setAttribute("user", user);
        request.setAttribute("lastMessagedUsers", lastMessagedUsers);
        request.getRequestDispatcher("chat/chat.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ChatDAO chatDAO = (ChatDAO) request.getServletContext().getAttribute("chatDAO");
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
        User user = (User) request.getSession().getAttribute("user");
        User other = userDAO.getUser(Integer.parseInt(request.getParameter("otherUserId")));
        List<User> lastMessagedUsers = chatDAO.lastMessages(user.getProfileId(), LAST_N_MESSAGED_USER);

        request.setAttribute("user", user);
        request.setAttribute("other", other);
        request.setAttribute("lastMessagedUsers", lastMessagedUsers);
        request.getRequestDispatcher("chat/chat.jsp").forward(request, response);
    }
}