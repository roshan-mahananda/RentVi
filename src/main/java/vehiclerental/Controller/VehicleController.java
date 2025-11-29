package Controller;

import com.ComputerScience.Vehicle.Rental.Model.Vehicle;
import com.ComputerScience.Vehicle.Rental.Service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    @PostMapping("/addVehicles")
    public List<Vehicle> addVehicles(@RequestBody List<Vehicle> vehicles){
        return vehicleService.addVehicles(vehicles);
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable int id) {
        return vehicleService.getVehicleById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable int id) {
        return vehicleService.deleteVehicle(id);
    }

    @GetMapping("/available")
    public List<Vehicle> getAvailableVehicles() {
        return vehicleService.getAvailableVehicles();
    }
}
