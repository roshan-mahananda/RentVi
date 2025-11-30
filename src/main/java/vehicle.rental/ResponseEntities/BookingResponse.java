package vehicle.rental.ResponseEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingResponse {
    private Long bookingId;

    private Long customerId;
    private String name;
    private String email;

    private Long vehicleId;
    private String type;
    private String brand;
    private double pricePerDay;

    private int totalDays;
    private double totalRent;
    private String message;
}
