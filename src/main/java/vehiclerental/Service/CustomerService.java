package Service;


import com.ComputerScience.Vehicle.Rental.Model.Customer;
import Repository.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    public Customer addCustomer(Customer customer){
        logger.info("Request to add customer : {}",customer.getName());
        if(customerRepo.existsByEmail(customer.getEmail())){
            logger.info("Customer can't be added. {} already exists.", customer.getEmail());
            throw new RuntimeException("Customer can't be added. Email already exists: " + customer.getEmail());
        }
        return customerRepo.save(customer);
    }

    public List<Customer> addCustomers(List<Customer> customers) {
        logger.info("Request to add {} customers", customers.size());
        return customerRepo.saveAll(customers); // saves all in one go
    }


    public void deleteCustomer(int id){
        if(!customerRepo.existsById(id)){
            logger.warn("Customer with {} id doesn't exist.",id);
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepo.deleteById(id);
        logger.info("Customer with id {} deleted.",id);
    }

    public List<Customer> getAllCustomer(){
        List<Customer> allCustomer = customerRepo.findAll();
        if(allCustomer.isEmpty()){
            logger.warn("Empty Customer list.");
        }
        return allCustomer;
    }

    public Customer getCustomerById(int id){
        logger.info("Request for getting customer by id : {}",id);
        // Find by ID, or throw a RuntimeException if it's not found
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }


    public Customer updateCustomer(int id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setAddress(updatedCustomer.getAddress());

        return customerRepo.save(existingCustomer);
    }
}
