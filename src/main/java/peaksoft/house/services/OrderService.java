package peaksoft.house.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import peaksoft.house.configuration.Configuration;
import peaksoft.house.models.Order;

import java.util.List;

/**
 * @author Beksultan
 */
public class OrderService implements AutoCloseable{

    private final EntityManagerFactory entityManagerFactory = Configuration.createEntityManagerFactory();

    public void save(Order newOrder) {
        // save a newOrder
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.detach(newOrder);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(Long orderId, Order newOrder) {
        // update order with id = orderId
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Order o set o.date = :date , o.price = :price, o.status = :status where o.id = :id")
                .setParameter("date", newOrder.getDate())
                .setParameter("price", newOrder.getPrice())
                .setParameter("id", orderId).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Order> findAllOrders() {
        // find all orders
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Order> orders = entityManager.createQuery("select o from Order o", Order.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return orders;
    }

    public Order findById(Long orderId) {
        // find order by id from database and return it!
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Order order = entityManager.createQuery("select o from Order o where o.id = ?1", Order.class)
                .setParameter(1, orderId).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return order;
    }

    public void deleteById(Long orderId) {
        // delete order from database where id = :orderId
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, orderId);
        entityManager.remove(order);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
