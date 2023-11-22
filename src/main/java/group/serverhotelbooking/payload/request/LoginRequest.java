package group.serverhotelbooking.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginRequest {
    @NotNull
    @Pattern(regexp = "^\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b$")
    @NotBlank(message = "email is not null!")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    @NotBlank(message = "password is not null!")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}