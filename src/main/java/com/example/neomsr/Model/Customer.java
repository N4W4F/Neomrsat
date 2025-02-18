package com.example.neomsr.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private Integer id;

    @Column(nullable = false)
    @NotEmpty(message = "Address cannot be empty")
    @Size(min = 3, max = 50, message = "Address must be between 3 and 50 characters")
    private String address;


    // Relations
    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    private Set<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonIgnore
    private Set<Review> reviews;
}
