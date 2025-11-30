package vehicle.rental.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String brand;
    private String model;
    private double price;
    private boolean available = true;

    @JsonManagedReference
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"customer", "vehicle"})
    private List<Booking> bookings;
}