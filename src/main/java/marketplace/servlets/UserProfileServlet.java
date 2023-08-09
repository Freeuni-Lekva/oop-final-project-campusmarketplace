package marketplace.servlets;

import marketplace.dao.PostDAO;
import marketplace.dao.UserDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "UserProfileServlet", value = "/profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        String userIdParam = request.getParameter("userId");
        if (userIdParam != null && !userIdParam.isEmpty()) {
            int userId = Integer.parseInt(userIdParam);
            User user = userDAO.getUser(userId);
            List<FeedPost> userFeedPosts = postDAO.getAllUserPosts(userId);
            request.setAttribute("user", user);
            request.setAttribute("userFeedPosts", userFeedPosts);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/profile.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}