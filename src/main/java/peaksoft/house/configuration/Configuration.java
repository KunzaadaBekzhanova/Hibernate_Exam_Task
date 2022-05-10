package peaksoft.house.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Environment;
import peaksoft.house.models.Address;
import peaksoft.house.models.Customer;
import peaksoft.house.models.Order;
import peaksoft.house.models.Supplier;

import java.sql.Connection;
import java.util.Properties;

public class Configuration {

    public static SessionFactory createSessionFactory() {
        return null;
    }


    public static EntityManagerFactory createEntityManagerFactory() {
        Properties properties = new Properties();
        properties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
        properties.setProperty(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.setProperty(Environment.USER, "postgres");
        properties.setProperty(Environment.PASS, "postgres");

        properties.setProperty(Environment.HBM2DDL_AUTO, "update");
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(Environment.SHOW_SQL, "true");

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.setProperties(properties);

        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(Supplier.class);

        return configuration.buildSessionFactory().unwrap(EntityManagerFactory.class);

    }
}
