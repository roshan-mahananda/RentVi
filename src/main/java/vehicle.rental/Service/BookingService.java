package vehicle.rental.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vehicle.rental.Model.Booking;
import vehicle.rental.Model.Customer;
import vehicle.rental.Model.Vehicle;
import vehicle.rental.Repository.BookingRepo;
import vehicle.rental.Repository.CustomerRepo;
import vehicle.rental.Repository.VehicleRepo;
import vehicle.rental.RequestEntities.BookingRequest;
import vehicle.rental.ResponseEntities.BookingResponse;

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
    public BookingResponse bookVehicle(BookingRequest bookingRequestDTO) {
        Customer customer = customerRepo.findById(bookingRequestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Vehicle vehicle = vehicleRepo.findById(bookingRequestDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (!vehicle.isAvailable()) {
            return new BookingResponse(-1,customer.getId(),null,null,-1,null,null,0,0,0,"vehicle is not available");
        }

        double totalRent = vehicle.getPrice() * bookingRequestDTO.getNumberOfDays();

        Booking booking = new Booking();
        booking.setVehicle(vehicle);
        booking.setCustomer(customer);
        booking.setNumberOfDays(bookingRequestDTO.getNumberOfDays());
        booking.setTotalRentPrice(totalRent);

        bookingRepo.save(booking);

        vehicle.setAvailable(false);
        vehicleRepo.save(vehicle);


        return new BookingResponse(booking.getId(),customer.getId(),customer.getName(),customer.getEmail(),vehicle.getId(),vehicle.getType(),vehicle.getBrand(),vehicle.getPrice(),bookingRequestDTO.getNumberOfDays(),totalRent,"Vehicle Booked");
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
