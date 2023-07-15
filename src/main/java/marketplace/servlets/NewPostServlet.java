package marketplace.servlets;

import marketplace.dao.PostDAO;
import marketplace.objects.Post;

import javax.servlet.RequestDispatcher;
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
import java.util.Collection;

@WebServlet(name = "NewPostServlet", value = "/newpost")
public class NewPostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        int profile_id = 1; // TODO: get profile_id from current session.
        System.out.println(request.getParameter("title"));
        System.out.println(request.getParameter("description"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Date date = Date.valueOf(request.getParameter("date"));
        double price = Double.parseDouble(request.getParameter("price"));
        Post post = new Post();
        post.setProfile_id(profile_id);
        post.setTitle(title);
        post.setPrice(price);
        post.setDescription(description);
        post.setDate(date);
        int post_id = postDAO.addNewPost(post);
        try {
            Collection<Part> parts = request.getParts();
            if (parts != null && parts.size() != 0) {
                int photoCount = 1;
                for (Part part : parts) {
                    String fileName = post.getPost_id() + "P" + photoCount + ".png";
                    photoCount++;
                    String savePath = "/images/";
                    savePath = getServletContext().getRealPath(savePath) + fileName;
                    try (InputStream inputStream = part.getInputStream()) {
                        Files.copy(inputStream, new File(savePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    postDAO.addPhoto(post_id, "/images/" + fileName);
                    if (photoCount == 2) {
                        postDAO.addMainPhoto(post_id, savePath + fileName);
                    }
                }
            } else {
                postDAO.addPhoto(post_id, "/images/default.png");
            }
        } catch (Exception e) {
            postDAO.addPhoto(post_id, "/images/default.png");
            e.printStackTrace();
        }
        post = postDAO.getPostById(post_id);
        request.setAttribute("post", post);
        post.setProfilesPost(true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index/.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}