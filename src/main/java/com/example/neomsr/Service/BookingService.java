package com.example.neomsr.Service;

import com.example.neomsr.ApiResponse.ApiException;
import com.example.neomsr.DTOout.BookingDTOout;
import com.example.neomsr.Model.Booking;
import com.example.neomsr.Model.MyUser;
import com.example.neomsr.Repository.AuthRepository;
import com.example.neomsr.Repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final AuthRepository authRepository;

    public List<Booking> getAllBookings(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (admin == null)
            throw new ApiException("Admin with ID: " + adminId + " was not found");

        if (!admin.getRole().equals("ADMIN"))
            throw new ApiException("You don't have access to this endpoint");

        return bookingRepository.findAll();
    }

    public List<BookingDTOout> getMyBookings(Integer customerId) {
        List<Booking> bookings = bookingRepository.findBookingByCustomerId(customerId);
        List<BookingDTOout> bookingDTOs = new ArrayList<>();

        for (Booking b : bookings) {
            bookingDTOs.add(new BookingDTOout(b.getBookingDate(), b.getDescription(), b.getStatus()));
        }

        return bookingDTOs;
    }

    public BookingDTOout getBookingById(Integer bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        return new BookingDTOout(booking.getBookingDate(), booking.getDescription(), booking.getStatus());
    }

    public void cancelBooking(Integer bookingId) {

    }
}
