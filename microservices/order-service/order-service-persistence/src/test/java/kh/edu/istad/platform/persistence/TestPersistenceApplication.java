package kh.edu.istad.platform.persistence;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "kh.edu.istad.platform.persistence.repository")
public class TestPersistenceApplication {

}
