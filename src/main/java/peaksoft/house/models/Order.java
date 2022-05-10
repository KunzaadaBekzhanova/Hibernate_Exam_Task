package peaksoft.house.models;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateTime")
    private LocalDateTime date;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private OrderStatus status;
    @OneToOne
    private Address pointA;
    @OneToOne
    private Address pointB;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Customer customer;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Supplier supplier;

    public Order(LocalDateTime date, BigDecimal price, OrderStatus status) {
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public Order(LocalDateTime date, BigDecimal price, OrderStatus status, Address pointA, Address pointB, Customer customer, Supplier supplier) {
        this.date = date;
        this.price = price;
        this.status = status;
        this.pointA = pointA;
        this.pointB = pointB;
        this.customer = customer;
        this.supplier = supplier;
    }
}
