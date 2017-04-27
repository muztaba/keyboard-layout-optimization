package com.seal.db;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.Objects;

/**
 * Created by seal on 4/26/2017.
 */
public class DB {

    private static volatile SessionFactory sessionFactory;

    public static Session getCurrentSession() {
        if (Objects.isNull(sessionFactory)) {
            synchronized (DB.class) {
                if (Objects.isNull(sessionFactory)) {
                    sessionFactory = init();
                }
            }
        }
        return sessionFactory.openSession();
    }

    private static SessionFactory init() {
        com.seal.Configuration config = com.seal.Configuration.load();
        Configuration configuration = new Configuration();
        configuration.driverConfiguration()
                .setDriverClassName(config.get("db.driver"))
                .setURI(config.get("db.url"))
                .setCredentials(config.get("db.username"), config.get("db.password"));
        return new SessionFactory(configuration, config.get("db.package.scan"));
    }
}
