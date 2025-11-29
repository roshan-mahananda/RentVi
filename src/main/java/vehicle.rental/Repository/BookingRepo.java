package vehicle.rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vehicle.rental.Model.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {
}
