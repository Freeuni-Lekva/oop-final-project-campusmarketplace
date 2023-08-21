package marketplace.servlets;

import marketplace.cookies.FavouriteCookies;
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
import java.io.IOException;

@WebServlet(name = "AddFavouriteServlet", value = "/addfavourite")
public class AddFavouriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        PhotoDAO photoDAO = (PhotoDAO) getServletContext().getAttribute("photoDAO");
        FavouritesDAO favouritesDAO = (FavouritesDAO) getServletContext().getAttribute("favouritesDAO");
        User user = (User) request.getSession().getAttribute("user");
        String post_id_string = request.getParameter("post_id");

        if (post_id_string == null) {
            response.sendRedirect("/feedposts");
            return;
        }
        int post_id = Integer.parseInt(post_id_string);
        Post post = postDAO.getPostById(post_id);
        post.setPhotos(photoDAO.getPhotos(post_id));
        if (user != null) {
            favouritesDAO.addFavourite(post_id, user.getProfileId());
            if (user.getProfileId() == post.getProfile_id())
                post.setProfilesPost(true);
        }
        FavouriteCookies.addFavourite(post_id, request,response);
        post.setFavourite(true);
        request.setAttribute("post", post);
        response.sendRedirect("/getfavourites");
    }
}
