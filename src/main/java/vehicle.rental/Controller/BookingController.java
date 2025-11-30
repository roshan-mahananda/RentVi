package vehicle.rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehicle.rental.Model.Booking;
import vehicle.rental.RequestEntities.BookingRequest;
import vehicle.rental.ResponseEntities.BookingResponse;
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
    public ResponseEntity<BookingResponse> bookVehicle(@RequestBody BookingRequest bookingRequestDTO) {
        return ResponseEntity.ok(bookingService.bookVehicle(bookingRequestDTO));
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
