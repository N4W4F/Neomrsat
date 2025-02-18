package com.example.neomsr.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "double not null")
    @NotNull(message = "rating Cannot be null")
    private Double rating;

    @Column(columnDefinition = "varchar(200) not null")
    @NotEmpty(message = "Comment cannot be empty")
    private String comment;


    // Relations
    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Booking booking;
}
