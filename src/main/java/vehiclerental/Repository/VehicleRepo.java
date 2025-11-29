package Repository;

import com.ComputerScience.Vehicle.Rental.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByAvailableTrue();
}

