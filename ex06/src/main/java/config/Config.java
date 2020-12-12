package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import service.TransferService;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"service", "main", "aop"})

@EnableAspectJAutoProxy
public class Config {

    @Bean
    public JdbcTemplate getTemplate(DataSource ds) {
        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql")
        );
        DatabasePopulatorUtils.execute(populator, ds);
        return new JdbcTemplate(ds);
    }


}