package com.movie_service.controller;

import com.movie_service.model.Booking;
import com.movie_service.model.Movie;
import com.movie_service.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addNewMovie(movie);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable("id") long id) {
        return movieService.fetchMovie(id);
    }

    @PostMapping("/book/{userId}/{movieId}")
    public Booking bookTickets(@PathVariable("userId") long userId, @PathVariable("movieId") long movieId, @RequestParam("seats") int seats) {

        System.out.println(userId);
        return movieService.bookTickets(userId, movieId, seats);
    }

    @PostMapping("/cancel/{bookingId}")
    public boolean cancelBooking(@PathVariable("bookingId") long bookingId){
        return movieService.cancelBooking(bookingId);
    }

    @GetMapping("/{movieId}/revenue")
    public double getTotalRevenue(@PathVariable("movieId") long movieId){
        return movieService.fetchTotalRevenue(movieId);
    }

    @GetMapping("/{movieId}/seats/available")
    public int getAvailableSeats(@PathVariable("movieId") long movieId){
        return movieService.fetchAvailableSeats(movieId);
    }

    @GetMapping("/user/{userId}/bookings")
    public List<Booking> getAllBookingsByUser(@PathVariable("userId") long userId){
        return movieService.fetchAllBookingsByUser(userId);
    }

}
