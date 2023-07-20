package marketplace.servlets;

import marketplace.constants.FilterConstants;
import marketplace.dao.PostDAO;
import marketplace.objects.Photo;
import marketplace.objects.Post;
import marketplace.utils.PostValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet(name = "NewPostServlet", value = "/newpost")
@MultipartConfig
public class NewPostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        int profile_id = 1; // TODO: get profile_id from current session.
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
        Post post = new Post();
        post.setProfile_id(profile_id);
        post.setTitle(title);
        post.setPrice(price);
        post.setDescription(description);
        post.setDate(date);
        int post_id = postDAO.addNewPost(post);
        for (String filter : FilterConstants.filters) {
            String checked = request.getParameter(filter);
            if (checked != null) {
                postDAO.addFilter(post_id, filter);
            }
        }
        try {
            Collection<Part> parts = request.getParts();
            if (parts != null && parts.size() != 0) {
                int photoCount = 1;
                for (Part part : parts) {
                    String fileName = post_id + "P" + photoCount + ".png";
                    photoCount++;
                    String savePath = "/images/";
                    savePath = getServletContext().getRealPath(savePath) + fileName;
                    try (InputStream inputStream = part.getInputStream()) {
                        Files.copy(inputStream, new File(savePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    postDAO.addPhoto(post_id, "images/" + fileName);
                    if (photoCount == 2) {
                        postDAO.addMainPhoto(post_id, "images/" + fileName);
                    }
                }
            } else {
                postDAO.addPhoto(post_id, "images/default.png");
            }
        } catch (Exception e) {
            postDAO.addPhoto(post_id, "images/default.png");
            e.printStackTrace();
        }
        post = postDAO.getPostById(post_id);
        ArrayList<Photo> photos = postDAO.getPhotos(post.getPost_id());
        post.setPhotos(photos);
        post.setProfilesPost(true);
        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}