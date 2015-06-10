package com.sevsu.util;

import com.sevsu.model.Enrollee;
import com.sevsu.model.Role;
import com.sevsu.model.Specialization;
import com.sevsu.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        // JDBC driver name and database URL
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String dbUrl = "jdbc:mysql://"+host+":"+port+"/abiturient?characterEncoding=UTF-8&amp;autoReconnect=true";

        //String JDBC_DRIVER = "com.mysql.jdbc.Driver";

        //  Database credentials
        String username = "adminK2Wmzkd";
        String password = "CJyS_qzVS4YI";

        // TODO add auto-reconnect=true
        Configuration config = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .setProperty("hibernate.connection.url", dbUrl)
                .setProperty("hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext")
                .setProperty("hibernate.order_updates", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Specialization.class)
                .addAnnotatedClass(Enrollee.class);

        // Build a Registry with our configuration properties
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();


        // create the session factory
        return config.buildSessionFactory(serviceRegistry);


        /*try {

            SessionFactory sessionFactory = new Configuration()
                    .configure("/com/mkyong/persistence/hibernate.cfg.xml")
                    .addResource("com/mkyong/common/Stock.hbm.xml")
                    .buildSessionFactory();

            return sessionFactory;

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }*/
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
/*

Configuration cfg = new Configuration()
.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect")
.setProperty("hibernate.connection.username", user);
.setProperty("hibernate.connection.password", password);
.setProperty("hibernate.connection.url", "jdbc:derby:memory:JH;create=true")
.setProperty("hibernate.order_updates", "true");
*/
}
