package com.example.neomrsat.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTOout {
    private LocalDateTime bookingDate;
    private String description;
    private String status;
}
