package com.example.neomrsat.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP not null")
    @NotNull(message = "Start Date and Time cannot be null")
    private LocalDateTime bookingDate;

    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotEmpty(message ="Status cannot be Empty!")
    @Pattern(regexp = "^(PENDING|APPROVED|REJECTED|COMPLETED|CANCELLED)")
    @Column(columnDefinition = "varchar(30) not null ")
    private String status = "PENDING";

    // Relations
    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    private  Zone zone;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "booking")
    private Review review;
}
