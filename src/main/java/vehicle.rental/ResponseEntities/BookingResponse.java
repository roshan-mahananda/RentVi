package vehicle.rental.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import vehicle.rental.Model.Customer;
import vehicle.rental.Model.Vehicle;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingResponseDTO {
    private Customer customer;
    private Vehicle vehicle;
    private int totalDays;
    private int totalRent;
}
