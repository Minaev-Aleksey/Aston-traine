package org.example.DZ2.ConnectionDB;

import org.example.DZ2.Entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionDB {
    public SessionFactory connectionDB(){
        return new Configuration()
                .configure()
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

    }
}
