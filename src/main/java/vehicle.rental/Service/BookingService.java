package vehicle.rental.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehicle.rental.Model.Booking;
import vehicle.rental.Model.Customer;
import vehicle.rental.Model.Vehicle;
import vehicle.rental.Repository.BookingRepo;
import vehicle.rental.Repository.CustomerRepo;
import vehicle.rental.Repository.VehicleRepo;
import vehicle.rental.RequestDTOs.BookingRequestDTO;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final VehicleRepo vehicleRepo;

    public BookingService(BookingRepo bookingRepo, CustomerRepo customerRepo, VehicleRepo vehicleRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @Transactional
    public String bookVehicle(BookingRequestDTO bookingRequestDTO) {
        Customer customer = customerRepo.findById(bookingRequestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Vehicle vehicle = vehicleRepo.findById(bookingRequestDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.isAvailable()) {
            return "Vehicle is not available for booking!";
        }

        double totalRentPrice = vehicle.getPrice() * bookingRequestDTO.getNumberOfDays();

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setVehicle(vehicle);
        booking.setNumberOfDays(bookingRequestDTO.getNumberOfDays());
        booking.setTotalRentPrice(totalRentPrice);

        bookingRepo.save(booking);

        vehicle.setAvailable(false);
        vehicleRepo.save(vehicle);

        return String.format(
                "Booking successful! Vehicle: %s | Days: %d | Total Rent: ₹%.2f",
                vehicle.getModel(), bookingRequestDTO.getNumberOfDays(), totalRentPrice
        );
    }

    @Transactional
    public String returnVehicle(int bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Vehicle vehicle = booking.getVehicle();

        vehicle.setAvailable(true);
        vehicleRepo.save(vehicle);

        return "Vehicle " + vehicle.getModel() + " returned successfully!";
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Booking getBookingById(int bookingId) {
        return bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}
