package marketplace.servlets;

import marketplace.annotation.Secure;
import marketplace.constants.FilterConstants;
import marketplace.cookies.FavouriteCookies;
import marketplace.dao.FavouritesDAO;
import marketplace.dao.FilterDAO;
import marketplace.dao.PhotoDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.Photo;
import marketplace.objects.Post;
import marketplace.objects.User;
import marketplace.search.SearchEngine;
import marketplace.utils.PostValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Secure
@WebServlet(name = "EditPostServlet", value = "/editpost")
public class EditPostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        FilterDAO filterDAO = (FilterDAO)getServletContext().getAttribute("filterDAO");
        FavouritesDAO favouritesDAO = (FavouritesDAO) getServletContext().getAttribute("favouritesDAO");
        PhotoDAO photoDAO = (PhotoDAO)getServletContext().getAttribute("photoDAO");
        User user = (User) request.getSession().getAttribute("user");
        int profile_id = user.getProfileId();
        int post_id = Integer.parseInt(request.getParameter("post_id"));
        Post post = postDAO.getPostById(post_id);
        if (post.getProfile_id() == profile_id) {
            String title = request.getParameter("title");
            if (!PostValidator.validateTitle(title)) {
                request.setAttribute("error", "Title must not be empty.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                try {
                    dispatcher.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            String description = PostValidator.descriptionValidate(request.getParameter("description"));
            Date date = Date.valueOf(LocalDate.now());
            double price = PostValidator.priceValidate(request.getParameter("price"));
            int filterCount = 0;
            for (String filter : FilterConstants.filters)
                if (request.getParameter(filter) != null)
                    filterCount++;
            if (filterCount == 0 || filterCount > FilterConstants.MAX_NUMBER_OF_FILTERS) {
                request.setAttribute("error", "Add at least 0 and at most " + FilterConstants.MAX_NUMBER_OF_FILTERS + " filters.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                try {
                    dispatcher.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            post = new Post(profile_id, post_id, title, price, description, date);
            filterDAO.removeAllFilters(post_id);
            for (String filter : FilterConstants.filters) {
                String checked = request.getParameter(filter);
                if (checked != null) {
                    filterDAO.addFilter(post_id, filter);
                }
            }
            postDAO.updatePost(post_id, post);
            post = postDAO.getPostById(post_id);
            ArrayList<Photo> photos = photoDAO.getPhotos(post.getPost_id());
            post.setPhotos(photos);
            post.setProfilesPost(true);
            post.setFavourite(favouritesDAO.isFavourite(post.getPost_id(), user.getProfileId()));
            post.setFavourite(post.isFavourite() | FavouriteCookies.isFavourite(post.getPost_id(), request, response));
            request.setAttribute("post", post);
        }
        SearchEngine searchEngine = (SearchEngine) getServletContext().getAttribute("searchEngine");
        searchEngine.update();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
