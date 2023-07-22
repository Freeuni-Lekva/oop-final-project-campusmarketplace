package marketplace.servlets;

import marketplace.annotation.Secure;
import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.Photo;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Secure
@WebServlet(name = "FeedServlet", value = "/feedposts")
public class FeedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        String pageString = request.getParameter("page");
        int page = 0;
        if (pageString != null)
            page = Integer.parseInt(pageString);
        ArrayList<FeedPost> feedPosts = postDAO.getAllFeedPosts(page);
        request.setAttribute("feedPosts", feedPosts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAttributes(ArrayList<FeedPost> feedPosts) {
        for (FeedPost post : feedPosts)
            System.out.println(post.toString());
    }
}