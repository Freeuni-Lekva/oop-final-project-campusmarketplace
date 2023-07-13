package marketplace.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterValidator {

    public static List<String> validate(UserDAO userDAO, String firstName, String surname, String password,
                                        String email, String phoneNumber, LocalDate birthDate) {
        List<String> errors = new ArrayList<>();

        if (Objects.equals(firstName, "")) {
            errors.add("Empty firstname");
        }

        if (Objects.equals(surname, "")) {
            errors.add("Empty surname");
        }

        if (!email.contains("@")) {
            errors.add("Invalid email");
        }

        if (!userDAO.existEmail(email)) {
            errors.add("Email not available");
        }

        if (password.length() < 5) {
            errors.add("password not strong enough");
        }

        return errors;
    }
}
