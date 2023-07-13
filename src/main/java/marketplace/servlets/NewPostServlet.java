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
import java.util.Collection;

@WebServlet(name = "NewPostServlet", value = "/newpost")
public class NewPostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PostDAO postDAO = (PostDAO) getServletContext().getAttribute("postDAO");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        double price = Double.parseDouble(request.getParameter("price"));
        String filter = request.getParameter("filter");
        Post post = new Post();
        post.setTitle(title);
        post.setPrice(price);
        post.setDescription(description);
        post.setDate(date);
        String post_id = postDAO.addNewPost(post);
        try {
            Collection<Part> parts = request.getParts();
            int photoCount = 1;
            for (Part part : parts) {
                String fileName = post.getPost_id() + "#" + photoCount + ".png";
                photoCount++;
                String savePath = "/images/" + fileName;
                try (InputStream inputStream = part.getInputStream()) {
                    Files.copy(inputStream, new File(savePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                postDAO.addPhoto(post_id, savePath);
            }
        } catch (Exception ignored) {
        }
        post = postDAO.getPostById(post_id);
        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception ignored) {
        }
    }


}