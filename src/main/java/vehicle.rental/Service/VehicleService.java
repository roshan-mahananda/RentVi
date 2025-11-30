package vehicle.rental.Service;

import org.springframework.stereotype.Service;
import vehicle.rental.Model.Vehicle;
import vehicle.rental.Repository.VehicleRepo;
import vehicle.rental.RequestEntities.VehicleRequest;
import vehicle.rental.ResponseEntities.VehicleResponse;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepo vehicleRepo;

    public VehicleService(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public VehicleResponse addVehicle(VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(vehicleRequest.getType());
        vehicle.setBrand(vehicleRequest.getBrand());
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setPrice(vehicleRequest.getPrice());

        vehicleRepo.save(vehicle);
        
        return new VehicleResponse(vehicle.getId(),
                vehicle.getType(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getPrice(),
                vehicle.isAvailable());
    }

    public List<Vehicle> addVehicles(List<Vehicle> vehicles) {
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
