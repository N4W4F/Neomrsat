package com.example.neomsr.Service;

import com.example.neomsr.ApiResponse.ApiException;
import com.example.neomsr.Model.MyUser;
import com.example.neomsr.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<MyUser> getAllUsers(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (admin == null)
            throw new ApiException("Admin was not found");

        if (admin.getRole().equals("ADMIN"))
            return authRepository.findAll();

        throw new ApiException("You don't have the permission to access this endpoint");
    }
}
