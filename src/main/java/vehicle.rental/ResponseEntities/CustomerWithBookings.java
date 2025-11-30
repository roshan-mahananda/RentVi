package vehicle.rental.ResponseEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import vehicle.rental.Model.Booking;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerWithBookings {
    private int id;
    private String name;
    private String email;
    private List<Booking> bookingList;
}
