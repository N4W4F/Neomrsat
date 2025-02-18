package com.example.neomsr.Repository;

import com.example.neomsr.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerById(Integer id);
}
