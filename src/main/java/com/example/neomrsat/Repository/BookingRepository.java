package com.example.neomrsat.Repository;

import com.example.neomrsat.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Booking findBookingById(Integer id);

    Booking findBookingByIdAndCustomerId(Integer bookingId, Integer customerId);

    List<Booking> findBookingByCustomerId(Integer customerId);

    List<Booking>findBookingByStatusAndCustomerId(String status, Integer customerId);
}
