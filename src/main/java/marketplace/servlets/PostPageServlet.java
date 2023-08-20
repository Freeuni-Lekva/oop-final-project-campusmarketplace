package marketplace.servlets;


import marketplace.annotation.Secure;
import marketplace.cookies.FavouriteCookies;
import marketplace.dao.FavouritesDAO;
import marketplace.dao.PhotoDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.Photo;
import marketplace.objects.Post;
import marketplace.objects.User;

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
        FavouritesDAO favouritesDAO = (FavouritesDAO) getServletContext().getAttribute("favouritesDAO");
        PhotoDAO photoDAO = (PhotoDAO) getServletContext().getAttribute("photoDAO");
        User user = (User) request.getSession().getAttribute("user");
        int profile_id = -1;
        if (user != null)
            profile_id = user.getProfileId();
        int post_id = Integer.parseInt(request.getParameter("post_id"));
        Post post = postDAO.getPostById(post_id);
        post.setProfilesPost(profile_id == post.getProfile_id());
        ArrayList<Photo> photos = photoDAO.getPhotos(post.getPost_id());
        post.setPhotos(photos);
        if (profile_id != -1)
            post.setFavourite(favouritesDAO.isFavourite(post.getPost_id(), profile_id));
        post.setFavourite(post.isFavourite() | FavouriteCookies.isFavourite(post.getPost_id(), request, response));
        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/post/post.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAttributes(Post post) {
        System.out.println(post.toString());
    }
}