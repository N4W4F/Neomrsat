package com.example.neomrsat.Configuration;

import com.example.neomrsat.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return  daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()

                .requestMatchers(
                        "/api/v1/login",
                        "/api/v1/neomrsat/customer/register"
                )
                .permitAll()

                .requestMatchers(
                        "/api/v1/neomrsat/booking/create/**",
                        "/api/v1/neomrsat/booking/get-my-bookings",
                        "/api/v1/neomrsat/booking/get-by-status/**",
                        "/api/v1/neomrsat/booking/get-by-id/**",
                        "/api/v1/neomrsat/booking/cancel/**",
                        "/api/v1/neomrsat/customer/update",
                        "/api/v1/neomrsat/review/add/**",
                        "/api/v1/neomrsat/review/update/**",
                        "/api/v1/neomrsat/review/delete/**",
                        "/api/v1/neomrsat/review/get-by-zone/**",
                        "/api/v1/neomrsat/zone/get-all",
                        "/api/v1/neomrsat/zone/get/**",
                        "/api/v1/neomrsat/zone/get-by-area/**",
                        "/api/v1/neomrsat/zone/get-by-capacity/**",
                        "/api/v1/neomrsat/zone/get-by-name/**",
                        "/api/v1/neomrsat/zone/get-by-price/**"
                        )
                .hasAnyAuthority("CUSTOMER", "ADMIN")

                .requestMatchers(
                        "/api/v1/user/get-all",
                        "/api/v1/neomrsat/booking/get-all",
                        "/api/v1/neomrsat/booking/update/**",
                        "/api/v1/neomrsat/booking/delete/**",
                        "/api/v1/neomrsat/customer/get-all",
                        "/api/v1/neomrsat/customer/delete/**",
                        "/api/v1/neomrsat/review/get-all",
                        "/api/v1/neomrsat/zone/create",
                        "/api/v1/neomrsat/zone/update/**",
                        "/api/v1/neomrsat/zone/delete/**",
                        "/api/v1/neomrsat/booking/approve/**",
                        "/api/v1/neomrsat/booking/reject/**",
                        "/api/v1/neomrsat/booking/complete/"
                        )
                .hasAuthority("ADMIN")

//                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
