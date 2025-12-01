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
import vehicle.rental.ResponseEntities.ReturnBookings;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final VehicleRepo vehicleRepo;

    public BookingService(BookingRepo bookingRepo,
                          CustomerRepo customerRepo,
                          VehicleRepo vehicleRepo) {
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
            return new BookingResponse(-1,
                    customer.getId(),
                    null,
                    null,
                    -1,
                    null,
                    null,
                    0,
                    0,
                    0,
                    "vehicle is not available");
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


        return new BookingResponse(booking.getId(),
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                vehicle.getId(),
                vehicle.getType(),
                vehicle.getBrand(),
                vehicle.getPrice(),
                bookingRequestDTO.getNumberOfDays(),
                totalRent,"Vehicle Booked");
    }

    @Transactional
    public ReturnBookings returnVehicle(int bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        Vehicle vehicle = booking.getVehicle();
        Customer customer = booking.getCustomer();

        vehicle.setAvailable(true);
        vehicleRepo.save(vehicle);

        return new ReturnBookings(customer.getName(),
                customer.getEmail(), vehicle.getId(),
                vehicle.getType(),vehicle.getBrand(),
                vehicle.getPrice(), booking.getNumberOfDays(),
                booking.getTotalRentPrice(),
                "Vehicle Returned Successfully");
    }

    public List<BookingResponse> getAllBookings() {
        List<Booking> bookingList = bookingRepo.findAll();
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for (Booking booking : bookingList){
            BookingResponse bookingResponse = new BookingResponse(booking.getId(),
                    booking.getCustomer().getId(),
                    booking.getCustomer().getName(),
                    booking.getCustomer().getEmail(),
                    booking.getVehicle().getId(),
                    booking.getVehicle().getType(),
                    booking.getVehicle().getBrand(),
                    booking.getVehicle().getPrice(),
                    booking.getNumberOfDays(),
                    booking.getTotalRentPrice(),
                    "Booking Detail");
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    public BookingResponse getBookingById(int bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return new BookingResponse(booking.getId(),
                booking.getCustomer().getId(),
                booking.getCustomer().getName(),
                booking.getCustomer().getEmail(),
                booking.getVehicle().getId(),
                booking.getVehicle().getType(),
                booking.getVehicle().getBrand(),
                booking.getVehicle().getPrice(),
                booking.getNumberOfDays(),
                booking.getTotalRentPrice(),
                "Booking");
    }
}
