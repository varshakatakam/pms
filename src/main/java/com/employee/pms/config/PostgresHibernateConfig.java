package com.employee.pms.config;

import javax.sql.DataSource;

import com.employee.pms.entity.Employee;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PostgresHibernateConfig {
    @Bean (name = "postgresDataSource")
    @ConfigurationProperties(prefix = "app.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name="postgresSessionFactory",destroyMethod = "close")
    public SessionFactory postgresSessionFactory(
            @Qualifier("postgresDataSource")
            DataSource dataSource) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(Employee.class);
        configuration.getProperties().put(
                "hibernate.connection.datasource",
                dataSource
        );
        configuration.setProperty(
                "hibernate.hbm2ddl.auto","update"
        );
        configuration.setProperty(
                "hibernate.show_sql","true"
        );
        return
                configuration.buildSessionFactory();
    }
    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresDataSource")
            DataSource dataSource){
                return new DataSourceTransactionManager(dataSource);
    }
}
