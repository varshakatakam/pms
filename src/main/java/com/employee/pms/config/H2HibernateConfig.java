package com.employee.pms.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.employee.pms.entity.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class H2HibernateConfig {

    @Bean(name = "h2DataSource")
    @ConfigurationProperties(prefix = "app.datasource.h2")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "h2SessionFactory", destroyMethod = "close")
    public SessionFactory h2SessionFactory(
            @Qualifier("h2DataSource") DataSource dataSource) {

        org.hibernate.cfg.Configuration configuration =
                new org.hibernate.cfg.Configuration();

        configuration.addAnnotatedClass(Employee.class);

        configuration.getProperties().put(
                "hibernate.connection.datasource",
                dataSource
        );

        configuration.setProperty(
                "hibernate.hbm2ddl.auto", "update"
        );

        configuration.setProperty(
                "hibernate.show_sql", "true"
        );

        return configuration.buildSessionFactory();
    }
    @Bean(name = "h2TransactionManager")
    public PlatformTransactionManager h2TransactionManager(
            @Qualifier("h2DataSource")
            DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
