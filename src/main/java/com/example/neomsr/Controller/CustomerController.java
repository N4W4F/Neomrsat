package com.example.neomsr.Controller;

import com.example.neomsr.ApiResponse.ApiResponse;
import com.example.neomsr.DTOin.CustomerDTOin;
import com.example.neomsr.Model.MyUser;
import com.example.neomsr.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/neomrsat/customer")
public class CustomerController {
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CustomerDTOin customerDTO) {
        customerService.register(customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("You have been registered successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCustomer(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(customerService.getAllCustomers(myUser.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal MyUser myUser ,
                                            @RequestBody @Valid CustomerDTOin customerDTO) {
        customerService.updateCustomer(myUser.getId(), customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Customer has been updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@AuthenticationPrincipal MyUser myUser ) {
        customerService.deleteCustomer(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer has been deleted successfully"));
    }
}
