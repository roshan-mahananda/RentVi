package vehicle.rental.Service;

import org.springframework.stereotype.Service;
import vehicle.rental.Model.Customer;
import vehicle.rental.Repository.CustomerRepo;
import vehicle.rental.RequestEntities.CustomerRequest;
import vehicle.rental.ResponseEntities.CustomerResponse;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService {

    CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo){
        this.customerRepo = customerRepo;
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest){
        if(customerRepo.existsByEmail(customerRequest.getEmail())){
            throw new RuntimeException("Customer can't be added. Email already exists: " + customerRequest.getEmail());
        }
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddress(customerRequest.getAddress());

        customerRepo.save(customer);

        return new CustomerResponse(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress());
    }

    public List<CustomerResponse> addCustomers(List<CustomerRequest> customerRequestList) {
        List<Customer> customers = new ArrayList<>();

        for (CustomerRequest customerRequest : customerRequestList) {
            Customer customer = new Customer(
                    customerRequest.getName(),
                    customerRequest.getAddress(),
                    customerRequest.getEmail()
            );
            customers.add(customer);
        }

        customerRepo.saveAll(customers);

        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerResponse customerResponse = new CustomerResponse(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getAddress()
            );
            customerResponseList.add(customerResponse);
        }

        return customerResponseList;
    }


    public void deleteCustomer(int id){
        if(!customerRepo.existsById(id)){
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepo.deleteById(id);
    }

    public List<Customer> getAllCustomer(){
        return customerRepo.findAll();
    }

    public Customer getCustomerById(int id){
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
