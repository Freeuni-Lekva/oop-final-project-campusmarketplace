package marketplace.dao.daoInterfaces;

import marketplace.objects.User;

import java.time.LocalDate;
import java.util.List;

public interface UserDAOInterface {
    void addUser(String firstName, String surname, String phoneNumber, String email, String passwordHash, LocalDate birthDate);
    boolean existsEmail(String email);
    User getUser(String email);

    List<Integer> getAllUserIds();
}
