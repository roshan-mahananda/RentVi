package Controller;

import com.ComputerScience.Vehicle.Rental.Model.Booking;
import com.ComputerScience.Vehicle.Rental.Service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public String bookVehicle(@RequestParam int customerId,
                              @RequestParam int vehicleId,
                              @RequestParam int numberOfDays) {
        return bookingService.bookVehicle(customerId, vehicleId, numberOfDays);
    }

    @PostMapping("/return/{bookingId}")
    public String returnVehicle(@PathVariable int bookingId) {
        return bookingService.returnVehicle(bookingId);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable int id) {
        return bookingService.getBookingById(id);
    }
}
