package com.example.neomrsat.DTOin;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTOin {
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters.")
    private String username;

    @NotEmpty(message = "Name cannot be empty.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 15 characters.")
    private String fullName;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email must be in valid email format.")
    @Size(min = 8, max = 50, message = "Email must be between 8 and 50 characters")
    private String email;

    @NotEmpty(message = "Phone number cannot be empty.")
    @Pattern(regexp = "^05\\d{8}$",
            message = "Phone number must start with '05' and be exactly 10 digits long.")
    private String phoneNumber;

    @NotEmpty(message = "Password cannot be empty.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*()\\-+=^])(?=\\S+$).{8,20}$",
            message = """
            Password must follow these rules: 
            - It must contain at least 8 and at most 20 characters.
            - It must contain at least one digit.
            - It must contain at least one uppercase letter.
            - It must contain at least one lowercase letter.
            - It must contain at least one special character (!@#$%&*()-+=^).
            - It must not contain any whitespace."""
    )
    private String password;

    @NotEmpty(message = "Address cannot be empty")
    @Size(min = 1, max = 100, message = "Customer Address must be between 1 and 100 characters")
    private String address;
}
