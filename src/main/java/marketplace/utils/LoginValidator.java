package marketplace.utils;

import java.util.Objects;

public class LoginValidator {
    public static boolean validate(UserDAO userDAO, String email, String password) {
        if (!userDAO.existsEmail(email)) {
            return false;
        }

        return Objects.equals(userDAO.getUser(email).getPasswordHash(), SecurityUtils.hashPassword(password));
    }
}
