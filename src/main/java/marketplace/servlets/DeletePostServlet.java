package marketplace.servlets;


import marketplace.dao.PostDAO;
import marketplace.objects.Photo;
import marketplace.objects.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;

@WebServlet(name = "DeletePostServlet", value = "/deletepost")

public class DeletePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        int profile_id = 1; // TODO: get profile_id from current session
        int post_id = (int) request.getAttribute("post_id");
        Post post = postDAO.getPostById(post_id);
        post.setPhotos(postDAO.getPhotos(post.getPost_id()));
        if (post.getProfile_id() == profile_id) {
            ArrayList<Photo> photos = post.getPhotos();
            for (Photo photo : photos) {
                postDAO.deletePhoto(photo.getPhoto_id());
                if (!photo.getPhoto_url().equals("/images/default.png")) {
                    String imagePath = getServletContext().getRealPath(photo.getPhoto_url());
                    File file = new File(imagePath);
                    file.delete();
                }
            }
            postDAO.deletePost(post_id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}