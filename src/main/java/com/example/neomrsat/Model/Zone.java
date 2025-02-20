package com.example.neomrsat.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(6) not null")
    @NotEmpty(message = "Zone Name cannot be empty")
    private String zoneName;


    @Column(columnDefinition = "decimal(10,2) not null")
    @NotNull(message = "Price Cannot be null")
    private BigDecimal pricePerHour;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Zone Capacity cannot be null")
    private Integer capacity;


    @Column(columnDefinition = "varchar(400) not null")
    @NotEmpty(message = "description cannot be empty")
    private String description;

    @Column(columnDefinition = "varchar(30) not null")
    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Area cannot be null")
    private Integer area;

    @Column(columnDefinition = "double not null")
    @NotNull(message = "Avg Rating Cannot be null")
    private Double avgRating = 0.0;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "Total ratings cannot be empty")
    private Integer totalRatings = 0;


    // Relations
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zone")
    @JsonIgnore
    private Set<ZoneSchedule> zoneSchedules;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zone")
    @JsonIgnore
    private Set<Booking> bookings;

}
