package marketplace.servlets;

import marketplace.objects.User;
import marketplace.utils.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import marketplace.dao.UserDAO;

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null){
            request.getSession().removeAttribute("error");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
        } else response.sendRedirect("/feedposts");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        request.getSession().removeAttribute("error");
        if (!LoginValidator.validate(userDAO, email, password)) {
            request.getSession().setAttribute("error", "Invalid login credentials");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.getUser(email);
        request.getSession().setAttribute("user", user);
        try {
            response.sendRedirect("/profile?userId="+Integer.toString(user.getProfileId()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
