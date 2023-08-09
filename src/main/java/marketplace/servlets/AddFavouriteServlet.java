package marketplace.servlets;

import marketplace.dao.FavouritesDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddFavouriteServlet", value = "/addfacourite")
public class AddFavouriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
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
        }
        int post_id = Integer.parseInt(post_id_string);
        if (user != null) {
            favouritesDAO.addFavourite(post_id, user.getProfileId());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
