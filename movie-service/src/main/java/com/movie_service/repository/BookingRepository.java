package com.movie_service.repository;

import com.movie_service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    public List<Booking> findByUserId(long userId);

    public List<Booking> findByMovieId(long movieId);

}
