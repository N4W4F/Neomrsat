package com.example.neomrsat.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTOout {
    private String fullName;
    private Double rating;
    private String comment;
}
