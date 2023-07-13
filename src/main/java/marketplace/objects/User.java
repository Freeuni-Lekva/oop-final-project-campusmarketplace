package marketplace.objects;

import java.util.Date;
import java.util.Objects;

public class User {
    private int profileId;
    private String firstName;
    private String surname;
    private String phoneNumber;
    private String email;
    private String passwordHash;
    private Date birthDate;

    public User(int profileId, String firstName, String surname, String phoneNumber, String email, String passwordHash, Date birthDate) {
        this.profileId = profileId;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.birthDate = birthDate;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return profileId == user.profileId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, firstName, surname, phoneNumber, email, passwordHash, birthDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "profileId=" + profileId +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
