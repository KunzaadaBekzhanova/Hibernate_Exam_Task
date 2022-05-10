package peaksoft.house;

import peaksoft.house.enums.OrderStatus;
import peaksoft.house.enums.SupplierStatus;
import peaksoft.house.models.Address;
import peaksoft.house.models.Customer;
import peaksoft.house.models.Order;
import peaksoft.house.models.Supplier;
import peaksoft.house.services.CustomerService;
import peaksoft.house.services.OrderService;
import peaksoft.house.services.SupplierService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static peaksoft.house.enums.OrderStatus.*;
import static peaksoft.house.enums.OrderStatus.DELIVERED;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception {
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService();
        SupplierService supplierService = new SupplierService();

//        customerService.save(new Customer("Steave",996777044345L));
//        customerService.save(new Customer("Ordinal",99677756315L));
//        customerService.save(new Customer("Mokki",996777395642L));
//        customerService.save(new Customer("Leony",996777936475L));

//        customerService.makeAnOrder(1L,new Order(LocalDateTime.now(), BigDecimal.valueOf(2000),OrderStatus.DELIVERED));

//        customerService.cancelOrder(1L,1L);

//        customerService.deleteById(1L);

//        customerService.update(2L,new Customer("Morining",996477936475L));

//        customerService.findAll().forEach(System.out::println);

//        customerService.findAllOrders(1L, OrderStatus.DELIVERED);
//        System.out.println( "Hello World!" );

//        orderService.save(new Order(LocalDateTime.now(), BigDecimal.valueOf(25500), DELIVERED));
//        System.out.println(orderService.findById(2L));

        Address address = new Address("Kyrgyzstan","Kadamzhai","Batken","Klubnaya-22");
        Order order = new Order(LocalDateTime.now(),BigDecimal.valueOf(1000), REQUEST);
        Supplier supplier = new Supplier("Bekzhan",772898736,SupplierStatus.FREE,List.of(order));

//        Customer customer = new Customer("Ravshanai",996771290970L,List.of(order),address);
//        Supplier supplier = new Supplier("Kunzaada",777044345,SupplierStatus.FREE,List.of(order));
        Customer customer1 = new Customer("Dinara",996771290970L,List.of(order),address);
        Order order1 = new Order(LocalDateTime.now(),BigDecimal.valueOf(1000), REQUEST,new Address("KG","Osh","Osh","Asanbai18"),
                new Address("RU","Torzhok","Tver","Leninksakaya"),customer1,supplier);
        customerService.save(customer1);
        supplierService.save(supplier);
        orderService.save(order1);

        
    }
}
