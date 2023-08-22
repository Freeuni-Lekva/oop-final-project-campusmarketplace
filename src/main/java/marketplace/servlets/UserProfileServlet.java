package marketplace.servlets;

import marketplace.dao.PostDAO;
import marketplace.dao.UserDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserProfileServlet", value = "/profile")
public class UserProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        UserDAO userDAO = (UserDAO) getServletContext().getAttribute("userDAO");
        String userIdParam = request.getParameter("userId");
        if (userIdParam != null && !userIdParam.isEmpty()) {
            int userId = Integer.parseInt(userIdParam);
            User user = userDAO.getUser(userId);
            List<FeedPost> userFeedPosts = postDAO.getAllUserPosts(userId);
            request.getSession().setAttribute("user_profile", user);
            request.getSession().setAttribute("userFeedPosts", userFeedPosts);
            request.getRequestDispatcher("/profile/profile.jsp").forward(request,response);
        }
    }
}