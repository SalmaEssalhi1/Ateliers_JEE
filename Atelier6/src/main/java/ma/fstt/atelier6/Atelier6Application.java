package ma.fstt.atelier6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("entities")
@EnableJpaRepositories("repository")
@ComponentScan(basePackages = {"controller", "service", "ma.fstt.atelier6"})
public class Atelier6Application {

    public static void main(String[] args) {

        SpringApplication.run(Atelier6Application.class, args);
    }

}
