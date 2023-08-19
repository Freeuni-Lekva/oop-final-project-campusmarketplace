package marketplace.servlets;

import marketplace.dao.UserDAO;
import marketplace.objects.User;
import marketplace.utils.RegisterValidator;
import marketplace.utils.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null){
            request.getSession().removeAttribute("errors");
            response.sendRedirect("register/register.jsp");
        }

        else response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String firstName = request.getParameter("firstName");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
        List<String> errors = RegisterValidator.validate(userDAO, firstName, surname, password, email, phoneNumber, birthDate);
        request.getSession().setAttribute("errors", errors);

        if (errors.isEmpty()) {
            request.getSession().removeAttribute("errors");
            String passwordHash = SecurityUtils.hashPassword(password);
            userDAO.addUser(firstName, surname, phoneNumber, email, passwordHash, birthDate);
            User user = userDAO.getUser(email);
            request.getSession().setAttribute("user", user);
        }

        // in register.jsp, if errors exist, display them, else display registration successful and redirect user to login
        response.sendRedirect("index.jsp");
    }
}
