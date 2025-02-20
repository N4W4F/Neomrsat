package com.example.neomrsat.Repository;

import com.example.neomrsat.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Review findReviewById(Integer id);

    @Query("SELECT r FROM Review r WHERE r.booking.zone.id = ?1")
    List<Review> findReviewByZoneId(Integer zoneId);
}
