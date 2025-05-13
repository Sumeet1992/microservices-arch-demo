package com.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-service-v1")
public interface MovieClient {

@PostMapping(value="/movies/book/{userId}/{movieId}")
ResponseEntity<BookingDTO> bookTickets(@PathVariable("userId") long userId, @PathVariable("movieId") long movieId, @RequestParam("seats") int seats);

@PostMapping("/movies/cancel/{bookingId}")
ResponseEntity<Boolean> cancelBooking(@PathVariable("bookingId") long bookingId);

}
