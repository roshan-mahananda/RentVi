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

import java.util.List;

@Service
public class BookingService {

    private final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final VehicleRepo vehicleRepo;

    public BookingService(BookingRepo bookingRepo, CustomerRepo customerRepo, VehicleRepo vehicleRepo) {
        this.bookingRepo = bookingRepo;
        this.customerRepo = customerRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @Transactional
    public String bookVehicle(int customerId, int vehicleId, int numberOfDays) {
        logger.info("Requested for booking for vehicle id : {} and customer id : {}",vehicleId,customerId);
        logger.warn("Checking if the user and vehicle both exists or not.");
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        logger.warn("Check if the vehicle is available to book or not.");
        if (!vehicle.isAvailable()) {
            return "Vehicle is not available for booking!";
        }

        logger.info("Calculate the total rent for customer.");
        double totalRentPrice = vehicle.getPrice() * numberOfDays;

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setVehicle(vehicle);
        booking.setNumberOfDays(numberOfDays);
        booking.setTotalRentPrice(totalRentPrice);

        logger.info("Booking is getting saved.");
        bookingRepo.save(booking);

        logger.info("Vehicle is set to unavailable.");
        vehicle.setAvailable(false);
        vehicleRepo.save(vehicle);

        return String.format(
                "Booking successful! Vehicle: %s | Days: %d | Total Rent: ₹%.2f",
                vehicle.getModel(), numberOfDays, totalRentPrice
        );
    }

    @Transactional
    public String returnVehicle(int bookingId) {
        logger.info("Requested for vehicle return for booking id : {}",bookingId);

        logger.warn("Checking if booking exists or not.");
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Vehicle vehicle = booking.getVehicle();

        logger.info("Vehicle id {} status updated.",booking.getVehicle().getId());
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
