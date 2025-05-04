package com.movie_service.service;

import com.movie_service.model.Booking;
import com.movie_service.model.Movie;
import com.movie_service.repository.BookingRepository;
import com.movie_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Movie addNewMovie(Movie movie){

        movie.setAvailableSeats(movie.getTotalSeats());
        movie.setTotalRevenue(0.0);
        Movie addedMovie = movieRepository.save(movie);

        if(addedMovie != null){
            return addedMovie;
        }
        return null;
    }

    public Movie fetchMovie(long movieId){

        Optional<Movie> movie = movieRepository.findById(movieId);
        return movie.orElse(null);
    }

    public Booking bookTickets(long userId, long movieId, int seats){

        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isPresent()){
            Booking newBooking = new Booking();
            Movie updatedMovie = movie.get();
            newBooking.setSeatsBooked(seats);
            newBooking.setMovieId(movieId);
            newBooking.setUserId(userId);

            double totalFareForSeats = seats * updatedMovie.getTicketPrice();
            int availableSeatsAfterBooking = updatedMovie.getAvailableSeats() - seats;
            if(availableSeatsAfterBooking >= 0){
                updatedMovie.setTotalRevenue(updatedMovie.getTotalRevenue() + totalFareForSeats);
                updatedMovie.setAvailableSeats(availableSeatsAfterBooking);
                movieRepository.save(updatedMovie);
                bookingRepository.save(newBooking);
                System.out.println(newBooking);
                return newBooking;
            }
        }
        return null;
    }

    public boolean cancelBooking(long bookingId){

        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isPresent()){
            Booking cancelledBooking = booking.get();

            long movieId = cancelledBooking.getMovieId();
            Optional<Movie> movie = movieRepository.findById(movieId);
            Movie updatedMovie = movie.get();
            updatedMovie.setTotalSeats(updatedMovie.getTotalSeats() + cancelledBooking.getSeatsBooked());
            updatedMovie.setTotalRevenue(updatedMovie.getTotalRevenue() - (cancelledBooking.getSeatsBooked() * updatedMovie.getTicketPrice()));

            movieRepository.save(updatedMovie);
            bookingRepository.save(cancelledBooking);
            return true;
        }
        return false;
    }

    public double fetchTotalRevenue(long movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isPresent()){
            return movie.get().getTotalRevenue();
        }
        return 0.0;
    }

    public int fetchAvailableSeats(long movieId){
        Optional<Movie> movie = movieRepository.findById(movieId);
        return movie.map(Movie::getAvailableSeats).orElse(0);
    }

    public List<Booking> fetchAllBookingsByUser(long userId){
        List<Booking> bookingList = bookingRepository.findByUserId(userId);

        if(bookingList != null && !bookingList.isEmpty()){
            return bookingList;
        }
        return null;

    }

}
