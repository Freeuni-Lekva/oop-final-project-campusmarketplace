package marketplace.servlets;

import marketplace.dao.UserDAO;
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
        if (request.getSession().getAttribute("user") == null)
            request.getRequestDispatcher("login.jsp").forward(request, response);
        else response.sendRedirect("/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));

        UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
        List<String> errors = RegisterValidator.validate(userDAO, firstName, surname, password, email, phoneNumber, birthDate);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else {
            String passwordHash = SecurityUtils.hashPassword(password);
            userDAO.addUser(firstName, surname, passwordHash, email, phoneNumber, birthDate);
        }

        // in register.jsp, if errors exist, display them, else display registration successful and redirect user to login
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
