package vehicle.rental.ResponseEntities;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleResponse {
    private int id;

    private String type;
    private String brand;
    private String model;
    private double price;
    private boolean available = true;
}
