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

@WebServlet(name = "login", value = "/")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doGet(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String firstName = httpServletRequest.getParameter("firstName");
        String surname = httpServletRequest.getParameter("surname");
        String password = httpServletRequest.getParameter("password");
        String email = httpServletRequest.getParameter("email");
        String phoneNumber = httpServletRequest.getParameter("phoneNumber");
        LocalDate birthDate = LocalDate.parse(httpServletRequest.getParameter("birthDate"));

        UserDAO userDAO = (UserDAO) httpServletRequest.getServletContext().getAttribute("userDAO");
        List<String> errors = RegisterValidator.validate(userDAO, firstName, surname, password, email, phoneNumber, birthDate);

        if (!errors.isEmpty()) {
            httpServletRequest.setAttribute("errors", errors);
            httpServletRequest.getRequestDispatcher("register.jsp").forward(httpServletRequest, httpServletResponse);
        } else {
            String passwordHash = SecurityUtils.hashPassword(password);
            userDAO.addUser(firstName, surname, passwordHash, email, phoneNumber, birthDate);
            httpServletRequest.getRequestDispatcher("login.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }
}
