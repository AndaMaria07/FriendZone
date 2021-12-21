package domain;

public class UserDto {

    private String firstName;
    private String lastName;
    private String date;

    public UserDto(String firstName, String lastName, String date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
    }

    @Override
    public String toString() {
        return firstName + " | " + lastName + " | " + date;
    }
}
