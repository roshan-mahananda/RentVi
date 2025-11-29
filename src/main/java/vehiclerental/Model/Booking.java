package Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties("bookings")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties("bookings")
    private Vehicle vehicle;

    private int numberOfDays;
    private double totalRentPrice;

    public Booking() {}

    public Booking(Customer customer, Vehicle vehicle, int numberOfDays, double totalRentPrice) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.numberOfDays = numberOfDays;
        this.totalRentPrice = totalRentPrice;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public double getTotalRentPrice() {
        return totalRentPrice;
    }

    public void setTotalRentPrice(double totalRentPrice) {
        this.totalRentPrice = totalRentPrice;
    }
}
