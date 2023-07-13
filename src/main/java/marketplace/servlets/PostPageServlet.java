package marketplace.servlets;


import marketplace.dao.PostDAO;
import marketplace.objects.Photo;
import marketplace.objects.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@WebServlet(name = "PostPageServlet", value = "/postpage")
public class PostPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        String post_id = (String) request.getSession().getAttribute("post_id");
        Post post = postDAO.getPostById(post_id);
        ArrayList<Photo> photos = postDAO.getPhotos(post.getPost_id());
        post.setPhotos(photos);
        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception ignored) {
        }
    }
}