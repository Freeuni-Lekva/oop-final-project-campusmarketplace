package marketplace.servlets;


import marketplace.database.DataBase;
import marketplace.objects.Photo;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(name = "DeletePostServlet", value = "/deletepost")

public class DeletePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        DataBase dataBase = (DataBase) getServletContext().getAttribute("dataBase");
        String post_id = (String) request.getAttribute("post_id");
        ArrayList<Photo> photos = dataBase.getPostById(post_id).getPhotos();
        for (int k = 0; k < photos.size(); k++)
            dataBase.deletePhoto(photos.get(k).getPhoto_id());
        dataBase.deletePost(post_id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (Exception ignored) {
        }
    }
}