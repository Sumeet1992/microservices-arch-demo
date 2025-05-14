package org.microservices.controller;


import org.microservices.dto.AuthRequestDTO;
import org.microservices.entity.UserCredential;
import org.microservices.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUserCredential(@RequestBody UserCredential user){
        return authenticationService.addUserCredential(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequestDTO authRequestDTO){
//        This authentication manager step is needed because anyone can pass any random username and random password
//        and get a token as there was not check anywhere.
//        So, this authentication manager checks if the userName & password is present in the Database.
//        If YES, only then return the token.
//        For the AuthenticationManager to connect to DB, we need to write a UserDetailsService class which will connect to DB.
//        Write it in the Configuration Class
        /*
        So, AuthenticationManager uses AuthenticationProvider class, for which we have created a bean
        in AuthConfig class.
        The AuthenticationProvider object uses the UserDetailsService object.
        We have created a CustomUserDetailsService class which implements the UserDetailsService class.
        This UserDetailsService class fetches the username and password from the Database and maps to a
        UserDetails class.
        Also, we have passed the username and password coming from the request "/token" to the AuthenticationManager.
        So, the AuthenticationManager first passes the username to the AuthenticationProvider which in turn
        passes the userName to the UserDetailsService class.
        This class fetches the username and password from the Database and maps to a
        UserDetails class using the method "loadUserByUsername".
        Then, the authenticationProvider matches the password passed from the request and
        the password fetched from the database which is stored in the UserDetails class after fetching from DB.
        If it matches, only then it confirms to the
        AuthenticationManager that the credentials exist in the database.
        */
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return authenticationService.generateToken(authRequestDTO.getUsername());
        }
        else{
            throw new RuntimeException("Invalid Access credentials");
        }
    }

    @GetMapping("validate")
    public String validateToken(@RequestParam("token") String token){
        authenticationService.validateToken(token);
        return "Token is Valid";
    }

}
