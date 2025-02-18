package com.example.neomsr.Service;

import com.example.neomsr.ApiResponse.ApiException;
import com.example.neomsr.DTOin.CustomerDTOin;
import com.example.neomsr.Model.Customer;
import com.example.neomsr.Model.MyUser;
import com.example.neomsr.Repository.AuthRepository;
import com.example.neomsr.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    public void register(CustomerDTOin customerDTO) {
        MyUser myUser = new MyUser();
        myUser.setUsername(customerDTO.getUsername());
        myUser.setEmail(customerDTO.getEmail());
        myUser.setPhoneNumber(customerDTO.getPhoneNumber());
        myUser.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        myUser.setRole("CUSTOMER");
        authRepository.save(myUser);

        Customer customer = new Customer();
        customer.setAddress(customerDTO.getAddress());
        customer.setUser(myUser);
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (admin == null)
            throw new ApiException("Admin with ID: " + adminId + " was not found");

        return customerRepository.findAll();
    }

    public void updateCustomer(Integer customerId, CustomerDTOin customerDTO) {
        MyUser customer = authRepository.findMyUserById(customerId);
        if (customer == null)
            throw new ApiException("Customer with ID: " + customerId + " was not found");

        customer.setUsername(customerDTO.getUsername());
        customer.setFullName(customerDTO.getFullName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        customer.getCustomer().setAddress(customerDTO.getAddress());
        authRepository.save(customer);
    }

    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }
        customerRepository.delete(customer);
    }
}
