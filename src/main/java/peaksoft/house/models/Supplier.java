package peaksoft.house.models;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.enums.SupplierStatus;

import java.util.List;

@Entity
@Table(name = "suppliers")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "status")
    private SupplierStatus status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier", fetch = FetchType.EAGER)
    private List<Order> orders;

    public Supplier(String fullName, int phoneNumber, SupplierStatus status) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public Supplier(String fullName, int phoneNumber, SupplierStatus status, List<Order> orders) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.orders = orders;
    }

    public void addOrder(Order newOrder) {
        orders.add(newOrder);
    }
}
