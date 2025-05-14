package org.microservices.service;

import com.netflix.discovery.converters.Auto;
import org.microservices.entity.UserCredential;
import org.microservices.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String addUserCredential(UserCredential userCredential){
//        Using password encoder to encode the password saved in the DB
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
        return "User added to the system";
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
