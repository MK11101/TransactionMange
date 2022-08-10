package com;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

@Configuration
@ComponentScan("com")
@EnableAspectJAutoProxy
public class SpringConfiguration {

    @Value("${driver}")
    String driver;
    @Value("${url}")
    String url;
    @Value("${name}")
    String name;
    @Value("${password}")
    String password;

    @Bean("dataSource")
    public DataSource test() throws PropertyVetoException, SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(name);
        dataSource.setPassword(password);
        return dataSource;
    }


}
