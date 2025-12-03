package vehicle.rental.ResponseEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReturnBookings {
    private String name;
    private String email;

    private int vehicleId;
    private String type;
    private String brand;
    private double pricePerDay;

    private int totalDays;
    private double totalRent;
    private String message;
}
