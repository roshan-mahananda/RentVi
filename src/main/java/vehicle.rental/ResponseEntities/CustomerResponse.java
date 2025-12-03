package vehicle.rental.ResponseEntities;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerResponse {
    private int id;
    private String name;
    private String email;
    private String address;
}
