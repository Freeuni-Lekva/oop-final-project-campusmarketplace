package marketplace.servlets;

import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.Photo;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(name = "FeedServlet", value = "/feedposts")
public class FeedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        ArrayList<FeedPost> feedPosts = postDAO.getAllFeedPosts();
        for (int k = 0; k < feedPosts.size(); k++) {
            Photo photo = postDAO.getPhotos(feedPosts.get(k).getPost_id()).get(0);
            feedPosts.get(k).setPhoto(photo);
        }
        request.setAttribute("feedPosts", feedPosts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception ignored) {
        }
    }
}