package vehicle.rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehicle.rental.Model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
}
