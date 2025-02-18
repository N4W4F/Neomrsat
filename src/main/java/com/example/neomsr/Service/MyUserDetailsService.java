package com.example.neomsr.Service;

import com.example.neomsr.ApiResponse.ApiException;
import com.example.neomsr.Model.MyUser;
import com.example.neomsr.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    public final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = authRepository.findMyUserByUsername(username);
        if (myUser == null) throw new ApiException("Wrong username or password");
        return  myUser;
    }
}
