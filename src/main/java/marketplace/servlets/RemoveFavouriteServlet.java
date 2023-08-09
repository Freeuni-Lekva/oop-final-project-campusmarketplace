package marketplace.servlets;

import marketplace.dao.FavouritesDAO;
import marketplace.dao.PhotoDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.Post;
import marketplace.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RemoveFavouriteServlet", value = "/removefacourite")
public class RemoveFavouriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        PhotoDAO photoDAO = (PhotoDAO) getServletContext().getAttribute("photoDAO");
        FavouritesDAO favouritesDAO = (FavouritesDAO) getServletContext().getAttribute("favouritesDAO");
        User user = (User) request.getSession().getAttribute("user");
        String post_id_string = request.getParameter("post_id");
        if (post_id_string == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        int post_id = Integer.parseInt(post_id_string);
        Post post = postDAO.getPostById(post_id);
        post.setPhotos(photoDAO.getPhotos(post_id));
        if (user != null) {
            if (favouritesDAO.isFavourite(post_id, user.getProfileId()))
                favouritesDAO.deleteFavourite(post_id, user.getProfileId());
            if (user.getProfileId() == post.getProfile_id())
                post.setProfilesPost(false);
        }
        post.setFavourite(false);
        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
