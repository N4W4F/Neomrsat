package com.example.neomrsat.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters.")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "Full Name cannot be empty.")
    @Size(min = 2, max = 50, message = "Full Name must be between 2 and 15 characters.")
    private String fullName;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email must be in valid email format.")
    @Size(min = 8, max = 50, message = "Email must be between 8 and 50 characters")
    private String email;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Phone number cannot be empty.")
    @Pattern(regexp = "^05\\d{8}$",
            message = "Phone number must start with '05' and be exactly 10 digits long.")
    private String phoneNumber;

    @Column(nullable = false)
    @NotEmpty(message = "Password cannot be empty.")
    private String password;

    @Column(nullable = false)
    @NotEmpty(message = "Role cannot be empty.")
    @Pattern(regexp = "^(CUSTOMER|ADMIN)$",
            message = "Role must be either 'CUSTOMER' or 'ADMIN'.")
    private String role;

    @Column(nullable = false)
    @NotNull(message = "Created At cannot be empty.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();


    // Relations
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Customer customer;


    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
