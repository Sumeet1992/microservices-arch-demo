package org.microservices.service;

import org.microservices.config.CustomUserDetails;
import org.microservices.entity.UserCredential;
import org.microservices.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = userCredentialRepository.findByName(username);
        return userCredential.map(user -> new CustomUserDetails(user)).orElseThrow(() -> new RuntimeException("User Details not found"));
    }
}
