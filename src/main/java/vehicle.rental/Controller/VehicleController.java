package vehicle.rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vehicle.rental.Model.Vehicle;
import vehicle.rental.RequestEntities.VehicleRequest;
import vehicle.rental.ResponseEntities.VehicleResponse;
import vehicle.rental.Service.VehicleService;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public ResponseEntity<VehicleResponse> addVehicle(@RequestBody VehicleRequest vehicleRequest) {
        return ResponseEntity.ok(vehicleService.addVehicle(vehicleRequest));
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
