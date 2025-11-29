package Repository;

import com.ComputerScience.Vehicle.Rental.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
}
