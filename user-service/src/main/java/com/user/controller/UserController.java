package com.user.controller;

import com.user.client.BookingDTO;
import com.user.model.User;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.addNewUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id){
        return userService.fetchUserById(id);
    }

    @PostMapping("/book/{userId}/{movieId}")
    public ResponseEntity<BookingDTO> bookTickets(@PathVariable("userId") long userId, @PathVariable("movieId") long movieId,
                                                  @RequestParam("seats") int seats,
                                                @RequestHeader("loggedInUser") String loggedInUser){
//        the loggedInUser header is the customized header passed from the AuthenticationFilter of the gateway service.
//        This way the individual microservice can know for which user the request has come
//        And extra logic like throttling based on the user can be applied using this information.
        System.out.println("THE LOGGED IN USER IS : "+ loggedInUser);

        System.out.println("userID : "+ userId);
        return userService.bookTicket(userId, movieId, seats);
    }

    @PostMapping("/cancel/{bookingId}")
    public boolean cancelTickets(@PathVariable("bookingId") long bookingId){
        return userService.cancelBooking(bookingId);
    }
}
