package com.user.service;

import com.user.client.BookingDTO;
import com.user.client.MovieClient;
import com.user.model.User;
import com.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebClient webClient;

    @Autowired
    MovieClient movieClient;

    public User addNewUser(User user){
        return userRepository.save(user);
    }

    public User fetchUserById(long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
          return user.get();
        }
        return null;
    }

    public ResponseEntity<BookingDTO> bookTicket(long userId, long movieId, int seats){

//        Mono<String> response = webClient.post()
//                .uri("/book/{userId}/{movieId}?seats={seats}", userId, movieId, seats)
//                .retrieve()
//                .bodyToMono(String.class);
//        return response.block();

        return movieClient.bookTickets(userId, movieId, seats);


    }

    public boolean cancelBooking(long bookingId){
        Mono<Boolean> response = webClient.post()
                .uri("/cancel/{bookingId}", bookingId)
                .retrieve()
                .bodyToMono(Boolean.class);
        return Boolean.TRUE.equals(response.block());
    }



}
