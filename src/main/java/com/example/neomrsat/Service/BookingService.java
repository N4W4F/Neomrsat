package com.example.neomrsat.Service;

import com.example.neomrsat.ApiResponse.ApiException;
import com.example.neomrsat.DTOout.BookingDTOout;
import com.example.neomrsat.Model.Booking;
import com.example.neomrsat.Model.Customer;
import com.example.neomrsat.Model.MyUser;
import com.example.neomrsat.Model.Zone;
import com.example.neomrsat.Repository.AuthRepository;
import com.example.neomrsat.Repository.BookingRepository;
import com.example.neomrsat.Repository.ZoneRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final AuthRepository authRepository;
    private final ZoneRepository zoneRepository;
    private final EmailService emailService;

    public void createBooking(Integer customerId, Integer zoneId, Booking booking) {
        MyUser customer = authRepository.findMyUserById(customerId);

        Zone zone = zoneRepository.findZoneById(zoneId);
        if (zone == null)
            throw new ApiException("Zone with ID: " + zoneId + " was not found");

        booking.setCustomer(customer.getCustomer());
        booking.setZone(zone);
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (!admin.getRole().equals("ADMIN"))
            throw new ApiException("You don't have access to this endpoint");

        return bookingRepository.findAll();
    }

    public void updateBooking(Integer adminId, Integer bookingId, Booking booking) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (!admin.getRole().equals("ADMIN"))
            throw new ApiException("You don't have access to this endpoint");

        Booking oldBooking = bookingRepository.findBookingById(bookingId);
        if (oldBooking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        oldBooking.setBookingDate(booking.getBookingDate());
        oldBooking.setDescription(booking.getDescription());
        oldBooking.setStatus(booking.getStatus());
        bookingRepository.save(oldBooking);
    }

    public void deleteBooking(Integer authId, Integer bookingId) {
        MyUser auth = authRepository.findMyUserById(authId);
        if (!auth.getRole().equals("ADMIN"))
            throw new ApiException("You don't have access to this endpoint");

        Booking booking = bookingRepository.findBookingById(bookingId);
        bookingRepository.delete(booking);
    }

    public List<BookingDTOout> getMyBookings(Integer customerId) {
        List<Booking> bookings = bookingRepository.findBookingByCustomerId(customerId);
        List<BookingDTOout> bookingDTOs = new ArrayList<>();

        for (Booking b : bookings) {
            bookingDTOs.add(new BookingDTOout(b.getBookingDate(), b.getDescription(), b.getStatus()));
        }

        return bookingDTOs;
    }

    public List<BookingDTOout> getBookingsByStatus(Integer customerId, String status) {
        List<Booking> bookings = bookingRepository.findBookingByStatusAndCustomerId(status, customerId);
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

    public void cancelBooking(Integer authId, Integer bookingId) {
        MyUser auth = authRepository.findMyUserById(authId);

        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        if (booking.getCustomer().getId().equals(authId) || auth.getRole().equals("ADMIN")) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
            return;
        }
        throw new ApiException("You don't have the permission to cancel this booking");
    }


    public void approveBooking(Integer authId, Integer bookingId) throws MessagingException {
        MyUser auth = authRepository.findMyUserById(authId);

        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        if (auth.getRole().equals("ADMIN")) {
            booking.setStatus("APPROVED");
            bookingRepository.save(booking);

            // Sending email to the customer after booking approval
            Customer customer = booking.getCustomer();
            sendBookingApprovedEmail(customer, booking);

            return;
        }
        throw new ApiException("You don't have the permission to approve this booking");
    }

    public void rejectBooking(Integer authId, Integer bookingId) throws MessagingException {
        MyUser auth = authRepository.findMyUserById(authId);

        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        if (auth.getRole().equals("ADMIN")) {
            booking.setStatus("REJECTED");
            bookingRepository.save(booking);
            sendBookingRejectedEmail(booking.getCustomer(), booking);
            return;
        }
        throw new ApiException("You don't have the permission to reject this booking");
    }

    public void completeBooking(Integer authId, Integer bookingId) throws MessagingException {
        MyUser auth = authRepository.findMyUserById(authId);

        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null)
            throw new ApiException("Booking with ID: " + bookingId + " was not found");

        if (auth.getRole().equals("ADMIN")) {
            booking.setStatus("COMPLETED");
            bookingRepository.save(booking);

            // Sending email to the customer after booking completion
            Customer customer = booking.getCustomer();
            sendBookingCompletedEmail(customer, booking);

            return;
        }
        throw new ApiException("You don't have the permission to complete this booking");
    }

    private void sendBookingApprovedEmail(Customer customer, Booking booking) throws MessagingException {
        String userEmail = customer.getUser().getEmail();
        String subject = "Booking Approved: Your booking has been approved";

        // HTML body containing both English and Arabic content
        String body = "<html><body dir='rtl' style='font-family: Arial, sans-serif;'>"
                // English content
                + "<p>Dear " + customer.getUser().getFullName() + ",</p><br>"
                + "<p>We are pleased to inform you that your booking has been approved.</p><br>"
                + "<p>Here are the details of your booking:</p><br>"
                + "<p><strong>Booking Description:</strong> " + booking.getDescription() + "</p><br>"
                + "<p><strong>Booking Status:</strong> " + booking.getStatus() + "</p><br>"
                + "<p><strong>Zone Name:</strong> " + booking.getZone().getZoneName() + "</p><br>"
                + "<p>We are excited to proceed with your booking. If you need any further assistance or have any questions, please feel free to contact us.</p><br>"
                + "<p>Best regards,</p>"
                + "<p>Neomrsat Team</p><br>"

                // Arabic content
                + "<p>عزيزي " + customer.getUser().getFullName() + "،</p><br>"
                + "<p>يسعدنا أن نعلمك أنه تم الموافقة على حجزك.</p><br>"
                + "<p>فيما يلي تفاصيل حجزك:</p><br>"
                + "<p><strong>وصف الحجز:</strong> " + booking.getDescription() + "</p><br>"
                + "<p><strong>حالة الحجز:</strong> " + booking.getStatus() + "</p><br>"
                + "<p><strong>اسم المنطقة:</strong> " + booking.getZone().getZoneName() + "</p><br>"
                + "<p>نحن متحمسون للمضي قدمًا في حجزك. إذا كنت بحاجة إلى مزيد من المساعدة أو لديك أي أسئلة، لا تتردد في التواصل معنا.</p><br>"
                + "<p>مع أطيب التحيات،</p>"
                + "<p>فريق Neomrsat</p>"
                + "</body></html>";

        emailService.sendEmail(userEmail, subject, body);
    }

    private void sendBookingRejectedEmail(Customer customer, Booking booking) throws MessagingException {
        String userEmail = customer.getUser().getEmail();
        String subject = "Booking Rejected: Your booking has been rejected";

        // HTML body containing both English and Arabic content
        String body = "<html><body dir='rtl' style='font-family: Arial, sans-serif;'>"
                // English content
                + "<p>Dear " + customer.getUser().getFullName() + ",</p><br>"
                + "<p>We regret to inform you that your booking has been rejected.</p><br>"
                + "<p>Here are the details of your booking:</p><br>"
                + "<p><strong>Booking Description:</strong> " + booking.getDescription() + "</p><br>"
                + "<p><strong>Booking Status:</strong> " + booking.getStatus() + "</p><br>"
                + "<p><strong>Zone Name:</strong> " + booking.getZone().getZoneName() + "</p><br>"
                + "<p>We are sorry for any inconvenience this may have caused. If you need further assistance or have any questions, please feel free to contact us.</p><br>"
                + "<p>Best regards,</p>"
                + "<p>Neomrsat Team</p><br>"

                // Arabic content
                + "<p>عزيزي " + customer.getUser().getFullName() + "،</p><br>"
                + "<p>نأسف لإبلاغك أنه تم رفض حجزك.</p><br>"
                + "<p>فيما يلي تفاصيل حجزك:</p><br>"
                + "<p><strong>وصف الحجز:</strong> " + booking.getDescription() + "</p><br>"
                + "<p><strong>حالة الحجز:</strong> " + booking.getStatus() + "</p><br>"
                + "<p><strong>اسم المنطقة:</strong> " + booking.getZone().getZoneName() + "</p><br>"
                + "<p>نعتذر عن أي إزعاج قد تسببت فيه هذه الحالة. إذا كنت بحاجة إلى مزيد من المساعدة أو لديك أي أسئلة، لا تتردد في التواصل معنا.</p><br>"
                + "<p>مع أطيب التحيات،</p>"
                + "<p>فريق Neomrsat</p>"
                + "</body></html>";

        emailService.sendEmail(userEmail, subject, body);
    }

    private void sendBookingCompletedEmail(Customer customer, Booking booking) throws MessagingException {
        String userEmail = customer.getUser().getEmail();
        String subject = "Booking Completed: Your booking has been successfully completed";

        // HTML body containing both English and Arabic content
        String body = "<html><body dir='rtl' style='font-family: Arial, sans-serif;'>"
                // English content
                + "<p>Dear " + customer.getUser().getFullName() + ",</p><br>"
                + "<p>We are pleased to inform you that your booking has been successfully completed.</p><br>"
                + "<p>Here are the details of your booking:</p><br>"
                + "<p><strong>Booking Description:</strong> " + booking.getDescription() + "</p><br>"
                + "<p><strong>Booking Status:</strong> " + booking.getStatus() + "</p><br>"
                + "<p><strong>Zone Name:</strong> " + booking.getZone().getZoneName() + "</p><br>"
                + "<p>We hope you had a great experience with us. If you need further assistance or have any questions, please feel free to contact us.</p><br>"
                + "<p>Best regards,</p>"
                + "<p>Neomrsat Team</p><br>"

                // Arabic content
                + "<p>عزيزي " + customer.getUser().getFullName() + "،</p><br>"
                + "<p>يسعدنا أن نعلمك أنه تم إتمام حجزك بنجاح.</p><br>"
                + "<p>فيما يلي تفاصيل حجزك:</p><br>"
                + "<p><strong>وصف الحجز:</strong> " + booking.getDescription() + "</p><br>"
                + "<p><strong>حالة الحجز:</strong> " + booking.getStatus() + "</p><br>"
                + "<p><strong>اسم المنطقة:</strong> " + booking.getZone().getZoneName() + "</p><br>"
                + "<p>نأمل أن تكون تجربتك معنا رائعة. إذا كنت بحاجة إلى مزيد من المساعدة أو لديك أي أسئلة، لا تتردد في التواصل معنا.</p><br>"
                + "<p>مع أطيب التحيات،</p>"
                + "<p>فريق Neomrsat</p>"
                + "</body></html>";

        emailService.sendEmail(userEmail, subject, body);
    }
}
