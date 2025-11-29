package vehicle.rental.Controller;

import org.springframework.web.bind.annotation.*;
import vehicle.rental.Model.Booking;
import vehicle.rental.RequestDTOs.BookingRequestDTO;
import vehicle.rental.Service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public String bookVehicle(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingService.bookVehicle(bookingRequestDTO);
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
