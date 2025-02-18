package com.example.neomsr.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoneDTOout {

    private String zoneName;
    private BigDecimal pricePerHour;
    private Integer capacity;
    private String description;
    private String location;
    private Integer area;
    private Double avgRating;

}
