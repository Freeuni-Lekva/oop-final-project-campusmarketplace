package marketplace.servlets;

import marketplace.annotation.Secure;
import marketplace.constants.FilterConstants;
import marketplace.dao.FilterDAO;
import marketplace.dao.PhotoDAO;
import marketplace.dao.PostDAO;
import marketplace.objects.Photo;
import marketplace.objects.Post;
import marketplace.objects.User;
import marketplace.search.SearchEngine;
import marketplace.utils.PostValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
import java.util.List;
import java.util.stream.Collectors;

@Secure
@WebServlet(name = "NewPostServlet", value = "/newpost")
@MultipartConfig
public class NewPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("error");
        request.getSession().removeAttribute("editPost");
        request.getRequestDispatcher("/upload/upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        FilterDAO filterDAO = (FilterDAO)getServletContext().getAttribute("filterDAO");
        PhotoDAO photoDAO = (PhotoDAO)getServletContext().getAttribute("photoDAO");
        User user = (User) request.getSession().getAttribute("user");
        int profile_id = user.getProfileId();
        String title = request.getParameter("title");
        if (!PostValidator.validateTitle(title)) {
            request.getSession().setAttribute("error", "Title must not be empty.");
            request.getRequestDispatcher("/upload/upload.jsp").forward(request, response);
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
            request.getSession().setAttribute("error", "Add at least 1 and at most " + FilterConstants.MAX_NUMBER_OF_FILTERS + " filters.");
            request.getRequestDispatcher("/upload/upload.jsp").forward(request, response);
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
                filterDAO.addFilter(post_id, filter);
            }
        }
        try {
            List<Part> parts = request.getParts().stream().filter(part -> "itemPhotos".equals(part.getName()) && part.getContentType().startsWith("image/")).collect(Collectors.toList());
            if (parts != null && parts.size() != 0) {
                int photoCount = 1;
                for (Part part : parts) {
                    String fileName = post_id + "P" + photoCount + ".png";
                    photoCount++;
                    String savePath = "/images/";
                    savePath = getServletContext().getRealPath(savePath) +"/"+ fileName;
                    try (InputStream inputStream = part.getInputStream()) {
                        Files.copy(inputStream, new File(savePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    photoDAO.addPhoto(post_id, "images/" + fileName);
                    if (photoCount == 2) {
                        postDAO.addMainPhoto(post_id, "images/" + fileName);
                    }
                }
            } else {
                photoDAO.addPhoto(post_id, "images/default.png");
            }
        } catch (Exception e) {
            photoDAO.addPhoto(post_id, "images/default.png");
            e.printStackTrace();
        }
        post = postDAO.getPostById(post_id);
        ArrayList<Photo> photos = photoDAO.getPhotos(post.getPost_id());
        post.setPhotos(photos);
        post.setProfilesPost(true);
        SearchEngine searchEngine = (SearchEngine) getServletContext().getAttribute("searchEngine");
        searchEngine.update();
        try {
            response.sendRedirect("/profile?userId="+Integer.toString(user.getProfileId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}