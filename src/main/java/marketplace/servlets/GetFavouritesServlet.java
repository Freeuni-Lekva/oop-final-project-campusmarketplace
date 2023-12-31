package marketplace.servlets;

import marketplace.cookies.FavouriteCookies;
import marketplace.dao.FavouritesDAO;
import marketplace.dao.PhotoDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.Post;
import marketplace.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "GetFavouritesServlet", value = "/getfavourites")
public class GetFavouritesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        FavouritesDAO favouritesDAO = (FavouritesDAO) getServletContext().getAttribute("favouritesDAO");
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        PhotoDAO photoDAO = (PhotoDAO) getServletContext().getAttribute("photoDAO");
        User user = (User) request.getSession().getAttribute("user");
        List<FeedPost> favouritePosts = new ArrayList<FeedPost>();
        if (user != null) {
            favouritePosts = favouritesDAO.favourites(user.getProfileId());
        }
        ArrayList<Integer> postIDs = (ArrayList<Integer>) FavouriteCookies.getFavouriteIDs(request, response);
        for (int id : postIDs) {
            Post post = postDAO.getPostById(id);
            if (post == null) {
                FavouriteCookies.removeFavourite(id, request, response);
                continue;
            }
            if (user != null && favouritesDAO.isFavourite(id, user.getProfileId()))
                continue;

            String mainPhoto = photoDAO.getPhotos(post.getPost_id()).get(0).getPhoto_url();
            FeedPost feedPost = new FeedPost(post.getPost_id(), mainPhoto, post.getTitle(), post.getPrice(), post.getDate());
            favouritePosts.add(feedPost);
        }
        request.getSession().setAttribute("favouritePosts", favouritePosts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/favourites/favourites.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}