package peaksoft.house.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private long phoneNumber;
    @OneToMany(cascade = {PERSIST,MERGE},orphanRemoval = true,mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Order> orders;
    @OneToOne(cascade = {PERSIST,MERGE},orphanRemoval = true)
    private Address address;
    public Customer(String fullName, long phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String fullName, long phoneNumber, List<Order> orders, Address address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
        this.address = address;
    }

    public void setOrder(Order newOrder) {
        orders.add(newOrder);
    }

}
