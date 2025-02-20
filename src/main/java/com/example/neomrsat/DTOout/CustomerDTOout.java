package com.example.neomrsat.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTOout {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
}
