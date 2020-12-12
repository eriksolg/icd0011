package config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"config, db"})
public class Config {

    @Bean
    public JdbcTemplate getTemplate(DataSource ds) {
        var populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql")
        );
        DatabasePopulatorUtils.execute(populator, ds);
        return new JdbcTemplate(ds);
    }
}
