package marketplace.servlets;

import marketplace.dao.FavouritesDAO;
import marketplace.dao.PhotoDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.FeedPost;
import marketplace.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetFavouritesServlet", value = "/getfavourites")
public class GetFavouritesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        FavouritesDAO favouritesDAO = (FavouritesDAO) getServletContext().getAttribute("favouritesDAO");
        User user = (User) request.getSession().getAttribute("user");
        List<FeedPost> favouritePosts = new ArrayList<FeedPost>();
        if (user != null) {
            favouritePosts = favouritesDAO.favourites(user.getProfileId());
        }
        request.setAttribute("favouritePosts", favouritePosts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}