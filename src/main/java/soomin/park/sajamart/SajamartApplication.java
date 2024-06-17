package soomin.park.sajamart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SajamartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SajamartApplication.class, args);
    }

}
