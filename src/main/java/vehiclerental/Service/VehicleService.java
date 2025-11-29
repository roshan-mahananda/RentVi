package Service;

import com.ComputerScience.Vehicle.Rental.Model.Vehicle;
import Repository.VehicleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final Logger logger = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepo vehicleRepo;

    public VehicleService(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    public List<Vehicle> addVehicles(List<Vehicle> vehicles) {
        logger.info("Request to add {} vehicles", vehicles.size());
        return vehicleRepo.saveAll(vehicles);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
    }

    public Vehicle updateVehicle(int id, Vehicle updatedVehicle) {
        Vehicle existing = getVehicleById(id);
        existing.setBrand(updatedVehicle.getBrand());
        existing.setModel(updatedVehicle.getModel());
        existing.setPrice(updatedVehicle.getPrice());
        existing.setAvailable(updatedVehicle.isAvailable());
        return vehicleRepo.save(existing);
    }

    public String deleteVehicle(int id) {
        if (!vehicleRepo.existsById(id)) {
            throw new RuntimeException("Vehicle not found with ID: " + id);
        }
        vehicleRepo.deleteById(id);
        return "Vehicle deleted successfully!";
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepo.findByAvailableTrue();
    }
}
