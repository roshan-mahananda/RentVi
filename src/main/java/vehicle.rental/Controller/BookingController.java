package vehicle.rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehicle.rental.RequestEntities.BookingRequest;
import vehicle.rental.ResponseEntities.BookingResponse;
import vehicle.rental.ResponseEntities.ReturnBookings;
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
    public ResponseEntity<ReturnBookings> returnVehicle(@PathVariable int bookingId) {
        return ResponseEntity.ok(bookingService.returnVehicle(bookingId));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable int id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
}
