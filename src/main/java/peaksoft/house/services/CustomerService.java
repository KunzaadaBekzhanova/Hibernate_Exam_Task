package peaksoft.house.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import peaksoft.house.configuration.Configuration;
import peaksoft.house.enums.OrderStatus;
import peaksoft.house.models.Customer;
import peaksoft.house.models.Order;

import java.util.List;

/**
 * @author Beksultan
 */
public class CustomerService implements AutoCloseable{
    private final EntityManagerFactory entityManagerFactory = Configuration.createEntityManagerFactory();


    public void save(Customer newCustomer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.persist(newCustomer);

        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public void makeAnOrder(Long customerId, Order newOrder) {
        // find customer and give order to customer
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Customer customer = entityManager.find(Customer.class,customerId);

        customer.setOrder(newOrder);
        entityManager.persist(customer);

        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public void cancelOrder(Long customerId, Long orderId) {
        // find customer with :customerId and find customer's order with id = :orderId
        // and setOrder status CANCELED
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Order o set o.status = :status where o.customer.id = :id and o.id = :oId").setParameter("status",OrderStatus.CANCELED)
        .setParameter("id",customerId)
                .setParameter("oId", orderId).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(Long customerId, Customer newCustomer) {
        // update customer with id = :customerId to newCustomer
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Customer  c set c.fullName = :fullName, c.phoneNumber = :phoneNumber where c.id = :id")
                .setParameter("fullName", newCustomer.getPhoneNumber())
                .setParameter("id", customerId).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Order> findAllOrders(Long customerId, OrderStatus orderStatus) {
        // find all orders by :orderStatus where customer id = :customerId
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Order> orders = entityManager.createQuery("select o from Order o join Customer c on o.id = c.id where c.id = :id and o.status = :status",Order.class)
                .setParameter("id",customerId)
                .setParameter("status",orderStatus).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return orders;
    }

    public List<Customer> findAll() {
        // return all customers from database
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Customer> customers = entityManager.createQuery("select c from Customer c", Customer.class).getResultList();
                entityManager.getTransaction().commit();
                entityManager.close();
        return customers;
    }

    public Customer findById(Long customerId) {
        // find customer with id = :customerId from database and return it
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager.createQuery("select  c " +
                "from Customer c where c.id = ?1", Customer.class).setParameter(1,customerId).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return customer;
    }

    public void deleteById(Long customerId) {
        // delete customer from database
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        entityManager.remove(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
