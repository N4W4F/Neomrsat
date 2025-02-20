package com.example.neomrsat.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class ZoneSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull(message = "Start Date and Time cannot be null")
    private LocalDateTime startDateTime;


    @Column(nullable = false)
    @NotNull(message = "End Date and Time cannot be null")
    private LocalDateTime endDateTime;

    /////////////////////
    @ManyToOne
    @JsonIgnore
    private Zone zone;
}
