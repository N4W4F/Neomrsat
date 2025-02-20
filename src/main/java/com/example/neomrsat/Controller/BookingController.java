package com.example.neomrsat.Controller;

import com.example.neomrsat.ApiResponse.ApiResponse;
import com.example.neomrsat.Model.Booking;
import com.example.neomrsat.Model.MyUser;
import com.example.neomrsat.Service.BookingService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/neomrsat/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create/{zoneId}")
    public ResponseEntity<?> createBooking(@AuthenticationPrincipal MyUser myUser,
                                           @PathVariable Integer zoneId,
                                           @RequestBody @Valid Booking booking) {
        bookingService.createBooking(myUser.getId(), zoneId, booking);
        return ResponseEntity.status(200).body(new ApiResponse("You have booked an appointment successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllBooking(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(bookingService.getAllBookings(myUser.getId()));
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateBooking(@AuthenticationPrincipal MyUser myUser,
                                           @PathVariable Integer bookingId,
                                           @RequestBody @Valid Booking booking) {
        bookingService.updateBooking(myUser.getId(), bookingId, booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking has been updated successfully"));
    }

    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<?> deleteBooking(@AuthenticationPrincipal MyUser myUser,
                                           @PathVariable Integer bookingId) {
        bookingService.deleteBooking(myUser.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking has been deleted successfully"));
    }

    @GetMapping("/get-my-bookings")
    public ResponseEntity<?> getMyBookings(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(bookingService.getMyBookings(myUser.getId()));
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?> getBookingByStatus(@AuthenticationPrincipal MyUser myUser,
                                                @PathVariable String status) {
        return ResponseEntity.status(200).body(bookingService.getBookingsByStatus(myUser.getId(), status));
    }

    @GetMapping("/get-by-id/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer bookingId) {
        return ResponseEntity.status(200).body(bookingService.getBookingById(bookingId));
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@AuthenticationPrincipal MyUser myUser,
                                           @PathVariable Integer bookingId) {
        bookingService.cancelBooking(myUser.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking has been cancelled successfully"));
    }
    @PutMapping("/approve/{bookingId}")
    public ResponseEntity approveBooking(@AuthenticationPrincipal MyUser myUser,
                                         @PathVariable Integer bookingId) throws MessagingException {
        bookingService.approveBooking(myUser.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking has been approved successfully"));
    }

    @PutMapping("/reject/{bookingId}")
    public ResponseEntity rejectBooking(@AuthenticationPrincipal MyUser myUser,
                                        @PathVariable Integer bookingId) throws MessagingException {
        bookingService.rejectBooking(myUser.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking has been rejected successfully"));
    }

    @PutMapping("/complete/{bookingId}")
    public ResponseEntity completeBooking(@AuthenticationPrincipal MyUser myUser,
                                          @PathVariable Integer bookingId) throws MessagingException {
        bookingService.completeBooking(myUser.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Booking has been completed successfully"));
    }
}
