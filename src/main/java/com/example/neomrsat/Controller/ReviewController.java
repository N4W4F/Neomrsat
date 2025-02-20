package com.example.neomrsat.Controller;

import com.example.neomrsat.ApiResponse.ApiResponse;
import com.example.neomrsat.Model.MyUser;
import com.example.neomrsat.Model.Review;
import com.example.neomrsat.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/neomrsat/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add/{bookingId}")
    public ResponseEntity<?> addReview(@AuthenticationPrincipal MyUser myUser,
                                       @PathVariable Integer bookingId,
                                       @RequestBody @Valid Review review) {
        reviewService.addReview(myUser.getId(), bookingId, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review has been added successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllReview(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(reviewService.getAllReviews(myUser.getId()));
    }

    @PutMapping("/update/{reviewId}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal MyUser myUser,
                                          @PathVariable Integer reviewId,
                                          @RequestBody @Valid Review review) {
        reviewService.updateReview(myUser.getId(), reviewId, review);
        return ResponseEntity.status(200).body(new ApiResponse("Review has been updated successfully"));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal MyUser myUser,
                                          @PathVariable Integer reviewId) {
        reviewService.deleteReview(myUser.getId(), reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Review has been deleted successfully"));
    }

    @GetMapping("/get-by-zone/{zoneId}")
    public ResponseEntity<?> getReviewsByZone(@PathVariable Integer zoneId) {
        return ResponseEntity.status(200).body(reviewService.getReviewsByZone(zoneId));
    }
}
