package com.example.neomrsat.Service;

import com.example.neomrsat.ApiResponse.ApiException;
import com.example.neomrsat.DTOout.ReviewDTOout;
import com.example.neomrsat.Model.*;
import com.example.neomrsat.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final AuthRepository authRepository;
    private final ZoneRepository zoneRepository;

    public void addReview(Integer customerId, Integer bookingId, Review review) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null)
            throw new ApiException("Customer with ID: " + customerId + " was not found");

        Booking booking = bookingRepository.findBookingByIdAndCustomerId(bookingId, customerId);
        if (booking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        if (!booking.getStatus().equals("COMPLETED"))
            throw new ApiException("You cannot review this booking until it's completed");

        review.setCustomer(customer);
        review.setBooking(booking);
        reviewRepository.save(review);

        Zone zone = booking.getZone();
        zone.setTotalRatings(zone.getTotalRatings() + 1);
        zoneRepository.save(zone);
        updateZoneRating(zone, review.getRating());
    }

    public List<Review> getAllReviews(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (!admin.getRole().equals("ADMIN"))
            throw new ApiException("You don't have access to this endpoint");

        return reviewRepository.findAll();
    }

    public void updateReview(Integer authId, Integer reviewId, Review review) {
        MyUser auth = authRepository.findMyUserById(authId);

        Review oldReview = reviewRepository.findReviewById(reviewId);
        if (oldReview == null)
            throw new ApiException("Review with ID: " + reviewId + " was not found");

        if (auth.getCustomer().getReviews().contains(oldReview) || auth.getRole().equals("ADMIN")) {
            oldReview.setRating(review.getRating());
            oldReview.setComment(review.getComment());
            reviewRepository.save(oldReview);

            Zone zone = oldReview.getBooking().getZone();
            updateZoneRating(zone, review.getRating());
            return;
        }
        throw new ApiException("You don't have access to update this review");
    }

    public void deleteReview(Integer authId, Integer reviewId) {
        MyUser auth = authRepository.findMyUserById(authId);

        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null)
            throw new ApiException("Review with ID: " + reviewId + " was not found");

        if (auth.getCustomer().getReviews().contains(review) || auth.getRole().equals("ADMIN")) {
            Zone zone = review.getBooking().getZone();
            zone.setTotalRatings(zone.getTotalRatings() - 1);
            zoneRepository.save(zone);
            updateZoneRating(zone, review.getRating());

            reviewRepository.delete(review);
            return;
        }
        throw new ApiException("You don't have access to delete this review");
    }

    public List<ReviewDTOout> getReviewsByZone(Integer zoneId) {
        List<Review> reviews = reviewRepository.findReviewByZoneId(zoneId);
        List<ReviewDTOout> reviewDTOs = new ArrayList<>();

        for (Review r : reviews) {
            reviewDTOs.add(new ReviewDTOout(r.getCustomer().getUser().getFullName(), r.getRating(), r.getComment()));
        }
        return reviewDTOs;
    }

    private void updateZoneRating(Zone zone, Double rating) {
        Double newRating = (zone.getAvgRating() + rating) / zone.getTotalRatings();
        zone.setAvgRating(newRating);
        zoneRepository.save(zone);
    }
}
