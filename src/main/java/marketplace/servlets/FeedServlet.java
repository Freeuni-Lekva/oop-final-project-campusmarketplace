package marketplace.servlets;

import marketplace.annotation.Secure;
import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.Photo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "FeedServlet", value = "/feedposts")
public class FeedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        int page = 0;
        ArrayList<FeedPost> feedPosts = new ArrayList<>();
        while (true) {
            if (postDAO.getAllFeedPosts(page) == null || postDAO.getAllFeedPosts(page).size() == 0) break;
            ArrayList<FeedPost> tempFeedPosts = postDAO.getAllFeedPosts(page);
            for (int i = 0; i < tempFeedPosts.size(); i++) {
                feedPosts.add(tempFeedPosts.get(i));
            }
            page++;
        }
        request.getSession().setAttribute("feedPosts", feedPosts);

        request.getRequestDispatcher("homepage/homepage.jsp").forward(request, response);
    }

    private static void printAttributes(ArrayList<FeedPost> feedPosts) {
        for (FeedPost post : feedPosts)
            System.out.println(post.toString());
    }
}