package peaksoft.house.services;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import peaksoft.house.configuration.Configuration;
import peaksoft.house.enums.OrderStatus;
import peaksoft.house.enums.SupplierStatus;
import peaksoft.house.models.Order;
import peaksoft.house.models.Supplier;

import java.util.List;
import static peaksoft.house.enums.SupplierStatus.BUSY;
/**
 * @author Beksultan
 */
public class SupplierService implements AutoCloseable{
    // create objects of repositories
    private final EntityManagerFactory entityManagerFactory = Configuration.createEntityManagerFactory();

    public void save(Supplier newSupplier) {
        // write your code here
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(newSupplier);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void update(Long supplierId, Supplier newSupplier) {
        // write your code here
        // you should find supplier with id = :supplierId
        // and replace with newSupplier
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Supplier s SET s.fullName = :fulName, s.phoneNumber = :phoneNumber, s.status = :status where s.id = :id")
                .setParameter("fullName", newSupplier.getFullName())
                .setParameter("phoneNumber", newSupplier.getPhoneNumber())
                .setParameter("id", supplierId).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Supplier> findAllSuppliers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Supplier> suppliers = entityManager.createQuery("select s from Supplier s", Supplier.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return suppliers;
    }

    public Supplier findById(Long supplierId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.createQuery("select s from Supplier s where s.id = ?1", Supplier.class).setParameter(1, supplierId).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        return supplier;
    }

    public void getOrder(Long supplierId, Long orderId) {
        // give free order to supplier with id = :supplierId
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.find(Supplier.class, supplierId);
        Order order = entityManager.find(Order.class, orderId);
        if (supplier.getStatus().equals(SupplierStatus.FREE) && order.getStatus().equals(OrderStatus.REQUEST)) {
            supplier.addOrder(order);
            Supplier supplier1 = entityManager.find(Supplier.class, supplierId);
            supplier1.setStatus(BUSY);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteById(Long supplierId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.find(Supplier.class, supplierId);
        entityManager.remove(supplier);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Order> findAllOrders(Long supplierId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Order> orders = entityManager.createQuery("select o from Order o join Supplier s on o.id = s.id where o.status = :st and s.id = :id", Order.class).setParameter("st", OrderStatus.DELIVERED).setParameter("id", supplierId).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return orders;
    }

    public List<Supplier> findAllBusySuppliers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Supplier> busy = entityManager.createQuery("select s from Supplier s where s.status = ?1", Supplier.class)
                .setParameter(1, BUSY).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return busy;
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
